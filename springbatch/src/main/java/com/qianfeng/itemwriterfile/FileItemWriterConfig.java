package com.qianfeng.itemwriterfile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.transform.LineAggregator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

/**
 * @program: springbatch
 * @description
 * @author: George
 * @create: 2022-05-03 09:08
 **/
@Configuration
public class FileItemWriterConfig {

    @Bean
    public FlatFileItemWriter<Customer> fileItemWriter() throws Exception {
        //把对象转成字符串  输出到文件中
        FlatFileItemWriter<Customer> writer = new FlatFileItemWriter<Customer>();
        //写入到这个文件中
        String path="H:\\BLACKHORSE-345\\springbatch\\batch001\\springbatch\\src\\main\\resources\\customer2.txt";
        writer.setResource(new FileSystemResource(path));

        //接下来就是转字符串了
        writer.setLineAggregator(new LineAggregator<Customer>() {
            ObjectMapper mapper=new ObjectMapper();

            @Override
            public String aggregate(Customer customer) {
                String str=null;
                try {
                    str=mapper.writeValueAsString(customer);
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
                return str;
            }
        });
        writer.afterPropertiesSet();
        return writer;
    }
}
