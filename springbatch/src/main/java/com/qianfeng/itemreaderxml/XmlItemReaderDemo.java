package com.qianfeng.itemreaderxml;

import com.qianfeng.itemreaderfile.Customer;
import com.qianfeng.springbatch.listener.MyJobListener;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.xml.StaxEventItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.oxm.xstream.XStreamMarshaller;

import java.util.HashMap;
import java.util.Map;

/**
 * @program: springbatch
 * @description
 * @author: George
 * @create: 2022-05-02 20:06
 **/
@Configuration
public class XmlItemReaderDemo {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;
    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    @Qualifier("xmlItemWriter")
    private ItemWriter<? super Customer> xmlItemWriter;



    /**
     * @Description: xmlItemWriterDemoJob
     * @Param: []
     * @Return: org.springframework.batch.core.Job
     */
    @Bean
    public Job xmlItemWriterDemoJob() {
        return jobBuilderFactory.get("xmlItemWriterDemoJob")
                .start(xmlItemWriterDemoStep())
                .listener(new MyJobListener())
                .build();
    }

    /**
     * @Description: xmlItemWriterDemoStep
     * @Param: []
     * @Return: org.springframework.batch.core.Step
     */
    @Bean
    public Step xmlItemWriterDemoStep() {

        return stepBuilderFactory.get("xmlItemWriterDemoStep")
                .<Customer,Customer>chunk(5)
                .reader(xmlFileReader())
                .writer(xmlItemWriter)
                .build();
    }

    @Bean
    @StepScope
    public StaxEventItemReader<Customer> xmlFileReader() {
        StaxEventItemReader<Customer> reader = new StaxEventItemReader<>();
        reader.setResource(new ClassPathResource("customer.xml"));
        //指定需要处理的根标签
        reader.setFragmentRootElementName("customer");
        //把XML转成java对象
        XStreamMarshaller  unmarshaller=new XStreamMarshaller();
        Map<String,Class> map=new HashMap<>();
        map.put("customer",Customer.class);
        unmarshaller.setAliases(map);
        reader.setUnmarshaller(unmarshaller);
        return reader;
    }

}
