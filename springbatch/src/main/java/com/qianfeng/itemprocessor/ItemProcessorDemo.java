package com.qianfeng.itemprocessor;


import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.support.CompositeItemProcessor;
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
 * @create: 2022-05-03 14:14
 **/
@Configuration
public class ItemProcessorDemo {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;
    @Autowired
    private StepBuilderFactory stepBuilderFactory;
    @Autowired
    @Qualifier("dbJdbcReader")
    private ItemReader<Customer> dbJdbcReader;
    @Autowired
    @Qualifier("fileItemWriter")
    private ItemWriter<Customer> fileItemWriter;
    @Autowired
    @Qualifier("firstNameUpperProcessor")
    private ItemProcessor<Customer,Customer> firstNameUpperProcessor;

    @Autowired
    @Qualifier("idFilterProcessor")
    private ItemProcessor<Customer,Customer> idFilterProcessor;

    @Bean
    public Job itemProcessorDemoJob2(){
        return jobBuilderFactory.get("itemProcessorDemoJob2")
                .start(itemProcessorDemoStep())
                .build();
    }

    @Bean
    public Step itemProcessorDemoStep() {
        return stepBuilderFactory.get("itemProcessorDemoStep")
                .<Customer,Customer>chunk(3)
                .reader(dbJdbcReader)
                .processor(process())
                .writer(fileItemWriter)
                .build();
    }
    //有多种数据处理的方式
    @Bean
    public CompositeItemProcessor<Customer, Customer> process(){
        CompositeItemProcessor<Customer, Customer> processor=new CompositeItemProcessor<Customer,Customer>();
        List<ItemProcessor<Customer,Customer>> delagates=new ArrayList<>();

        delagates.add(firstNameUpperProcessor);
        delagates.add(idFilterProcessor);

        processor.setDelegates(delagates);
        return processor;

    }
}
