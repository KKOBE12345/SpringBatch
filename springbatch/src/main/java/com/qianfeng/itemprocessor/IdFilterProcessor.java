package com.qianfeng.itemprocessor;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.context.annotation.Configuration;

/**
 * @program: springbatch
 * @description
 * @author: George
 * @create: 2022-05-03 14:25
 **/
@Configuration
public class IdFilterProcessor implements ItemProcessor<Customer,Customer> {
    @Override
    public Customer process(Customer item) throws Exception {
        if(item.getId()%2==0){
            return item;
        }else {
            return null;  //相当于把那个对象过滤了
        }

    }
}
