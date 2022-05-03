package com.qianfeng.retry;

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
 * @create: 2022-05-03 15:07
 **/
@Configuration
public class RetryDemo {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;
    @Autowired
    private StepBuilderFactory stepBuilderFactory;
    @Autowired
    @Qualifier("retryItemWriter")
    private ItemWriter<String> retryItemWriter;
    @Autowired
    private ItemProcessor<String,String> retryItemProcessor;


    @Bean
    public Job retryDemoJob(){
        return jobBuilderFactory.get("retryDemoJob")
                .start(retryDemoStep())
                .build();
    }

    @Bean
    public Step retryDemoStep() {
        return stepBuilderFactory.get("retryDemoStep")
                .<String,String>chunk(3)
                .reader(reader())
                .processor(retryItemProcessor)
                .writer(retryItemWriter)
                .faultTolerant()   //容错
                .retry(CustomRetryException.class)//只有发生这个异常 才让他去重试
                .retryLimit(5)//最多让你重试5次
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
