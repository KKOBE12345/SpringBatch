package com.qianfeng.itemwriterdb;

import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * @program: springbatch
 * @description
 * @author: George
 * @create: 2022-05-03 08:43
 **/
@Configuration
public class ItemWriterDbConfig {
    @Autowired
    private DataSource dataSource;
    @Bean
    public JdbcBatchItemWriter<Customer> itemWriterDb(){
        JdbcBatchItemWriter<Customer> writer = new JdbcBatchItemWriter<>();
        writer.setDataSource(dataSource);
        writer.setSql("insert into customer(id,firstName,lastName,birthday) values "+
                "(:id,:firstName,:lastName,:birthday)");
        //将语句中的占位符替换为属性值
        writer.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<Customer>());
        return writer;

    }
}
