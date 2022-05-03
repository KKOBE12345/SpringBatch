package com.qianfeng.itemwriterxml;


import org.springframework.batch.item.database.JdbcPagingItemReader;
import org.springframework.batch.item.database.Order;
import org.springframework.batch.item.database.support.MySqlPagingQueryProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.RowMapper;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * @program: springbatch
 * @description
 * @author: George
 * @create: 2022-05-03 09:01
 **/

@Configuration
public class DbJdbcReaderConfig {
    @Autowired
    private DataSource dataSource;

    @Bean
    public JdbcPagingItemReader<Customer> dbJdbcReader() {
        JdbcPagingItemReader<Customer> reader=new JdbcPagingItemReader<Customer>();
        reader.setDataSource(dataSource);
        reader.setFetchSize(3);
        //把读取到的记录  装换成User对象
        reader.setRowMapper(new RowMapper<Customer>() {
            @Override
            public Customer mapRow(ResultSet resultSet, int i) throws SQLException {
                Customer customer = new Customer();
                customer.setId(resultSet.getLong(1));
                customer.setFirstName(resultSet.getString(2));
                customer.setLastName(resultSet.getString(3));
                customer.setBirthday(resultSet.getString(4));

                return customer;
            }
        });
        //指定SQL语句
        MySqlPagingQueryProvider provider=new MySqlPagingQueryProvider();
        provider.setSelectClause("id,firstName,lastName,birthday");
        provider.setFromClause("from customer");
        //根据那个字段实行排序
        Map<String, Order> sort=new HashMap<>(1);
        sort.put("id", Order.ASCENDING);
        provider.setSortKeys(sort);

        reader.setQueryProvider(provider);
        System.out.println(123567890);
        return reader;
    }
}
