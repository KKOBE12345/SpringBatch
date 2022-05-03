package com.qianfeng.itemprocessor;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.context.annotation.Configuration;

/**
 * @program: springbatch
 * @description
 * @author: George
 * @create: 2022-05-03 14:19
 **/
@Configuration
public class FirstNameUpperProcessor implements ItemProcessor<Customer,Customer> {
    @Override
    public Customer process(Customer item) throws Exception {
        Customer customer= new Customer();
        customer.setId(item.getId());
        customer.setFirstName(item.getFirstName().toUpperCase());
        customer.setLastName(item.getLastName());
        customer.setBirthday(item.getBirthday());
        return customer;
    }
}
