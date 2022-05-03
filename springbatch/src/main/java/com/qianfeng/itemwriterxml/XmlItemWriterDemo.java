package com.qianfeng.itemwriterxml;


import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;

/**
 * @program: springbatch
 * @description
 * @author: George
 * @create: 2022-05-03 09:21
 **/
//@Configuration
public class XmlItemWriterDemo {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;
    @Autowired
    private StepBuilderFactory stepBuilderFactory;
    @Autowired
    @Qualifier("dbJdbcReader")
    private ItemReader<Customer> dbJdbcReader;
    @Autowired
    @Qualifier("xmlItemWriter")
    private ItemWriter<Customer> xmlItemWriter;

    @Bean
    public Job xmlItemWriterDemoJob4(){
        return jobBuilderFactory.get("xmlItemWriterDemoJob94")
                .start(xmlItemWriterDemoStep())
                .build();

    }

    @Bean
    public Step xmlItemWriterDemoStep() {

        return stepBuilderFactory.get("xmlItemWriterDemoStep")
                .<Customer,Customer>chunk(3)
                .reader(dbJdbcReader)
                .writer(xmlItemWriter)
                .build();
    }

}
