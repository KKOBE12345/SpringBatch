package com.qianfeng.itemreader;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;

/**
 * @program: springbatch
 * @description
 * @author: George
 * @create: 2022-05-02 17:09
 **/
@Configuration
public class ItemReaderDemo {
    @Autowired
    private JobBuilderFactory jobBuilderFactory;
    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job itemReaderDemoJob(){
        return jobBuilderFactory.get("itemReaderDemoJob")
                .start(itemReaderDemoStep())
                .build();
    }

    @Bean
    public Step itemReaderDemoStep() {
        return stepBuilderFactory.get("itemReaderDemoStep")
                .<String,String>chunk(3)
                .reader(itemReaderDemoRead())
                .writer(list -> {
                    for(String item:list){
                        System.out.println(item+"...");
                    }
                }).build();
    }

    @Bean
    public MyReader itemReaderDemoRead() {
        List<String> data = Arrays.asList("kobe", "jordan", "james", "curry", "dog", "cat", "pig");
        return new MyReader(data);
    }
}
