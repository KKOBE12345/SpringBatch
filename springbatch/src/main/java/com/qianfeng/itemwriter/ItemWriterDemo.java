package com.qianfeng.itemwriter;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;

/**
 * @program: springbatch
 * @description
 * @author: George
 * @create: 2022-05-03 08:18
 **/
@Configuration
public class ItemWriterDemo {
    @Autowired
    private JobBuilderFactory jobBuilderFactory;
    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    @Qualifier("myWriter")
    private ItemWriter<String> myWriter;

    @Bean
    public Job itemWriterDemoJob(){
        return jobBuilderFactory.get("itemWriterDemoJob")
                .start(itemWriterDemoStep())
                .build();
    }

    @Bean
    public Step itemWriterDemoStep() {
        return stepBuilderFactory.get("itemWriterDemoStep")
                .<String,String>chunk(3)
                .reader(myReader())
                .writer(myWriter)
                .build();
    }

    @Bean
    public ItemReader<String> myReader() {
        ArrayList<String> items = new ArrayList<>();
        for(int i=1;i<=24;i++){
            items.add("java"+i);
        }
        return new ListItemReader<String>(items);
    }
}
