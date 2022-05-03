package com.qianfeng.itemreaderxml;

import com.qianfeng.itemreaderfile.Customer;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @program: springbatch
 * @description
 * @author: George
 * @create: 2022-05-02 20:25
 **/

@Component("xmlItemWriter")
public class XmlItemWriter implements ItemWriter<Customer> {
    @Override
    public void write(List<? extends Customer> list) throws Exception {
        for (Customer customer : list) {
            System.out.println(customer);
        }

    }
}
