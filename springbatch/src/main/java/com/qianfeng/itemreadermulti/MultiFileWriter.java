package com.qianfeng.itemreadermulti;

import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @program: springbatch
 * @description
 * @author: George
 * @create: 2022-05-02 21:46
 **/
@Component("multiFileWriter")
public class MultiFileWriter implements ItemWriter<Customer> {
    @Override
    public void write(List<? extends Customer> list) throws Exception {
        for (Customer customer : list) {
            System.out.println(customer);
        }
    }
}
