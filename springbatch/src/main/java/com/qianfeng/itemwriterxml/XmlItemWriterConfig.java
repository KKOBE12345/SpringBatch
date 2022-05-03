package com.qianfeng.itemwriterxml;

import org.springframework.batch.item.xml.StaxEventItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.oxm.xstream.XStreamMarshaller;

import java.util.HashMap;
import java.util.Map;

/**
 * @program: springbatch
 * @description
 * @author: George
 * @create: 2022-05-03 09:26
 **/
@Configuration
public class XmlItemWriterConfig {
    @Bean
    public StaxEventItemWriter<Customer> xmlItemWriter() throws Exception {
        StaxEventItemWriter<Customer> writer=new StaxEventItemWriter<Customer>();
        XStreamMarshaller marshaller=new XStreamMarshaller();
        Map<String,Class> aliases=new HashMap<>();
        aliases.put("customer", Customer.class);
        marshaller.setAliases(aliases);

        //指定根标签
        writer.setRootTagName("customers");
        writer.setMarshaller(marshaller);
        //指定写到哪个文件中
        String path="H:\\kobe456.xml";
        writer.setResource(new FileSystemResource(path));

        System.out.println("kobe已经执行到这边了");

        //检查一下写出操作
        writer.afterPropertiesSet();
        System.out.println(writer.toString());
        return writer;
    }
}
