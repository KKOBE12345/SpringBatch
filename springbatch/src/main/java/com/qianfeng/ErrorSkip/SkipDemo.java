package com.qianfeng.ErrorSkip;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

/**
 * @program: springbatch
 * @description
 * @author: George
 * @create: 2022-05-03 15:35
 **/
@Configuration
public class SkipDemo {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;
    @Autowired
    private StepBuilderFactory stepBuilderFactory;
    @Autowired
    @Qualifier("skipItemWriter")
    private ItemWriter<String> skipItemWriter;
    @Autowired
    @Qualifier("skipItemProcessor")
    private ItemProcessor<String,String> skipItemProcessor;

    @Autowired
    private MySkipListener mySkipListener;


    @Bean
    public Job skipDemoJob5(){
        return jobBuilderFactory.get("skipDemoJob5")
                .start(skipDemoStep())
                .build();
    }

    @Bean
    public Step skipDemoStep() {
        return stepBuilderFactory.get("skipDemoStep")
                .<String,String>chunk(10)
                .reader(reader())
                .processor(skipItemProcessor)
                .writer(skipItemWriter)
                .faultTolerant()
                .skip(CustomRetryException.class)
                .skipLimit(3)
                .listener(mySkipListener)
                .build();
    }

    @Bean
    @StepScope
    public ItemReader<String> reader() {
        List<String> items=new ArrayList<>();
        for (int i = 0; i < 60; i++) {
            items.add(String.valueOf(i));
        }
        ListItemReader<String> reader=new ListItemReader<>(items);
        return reader;
    }
}
