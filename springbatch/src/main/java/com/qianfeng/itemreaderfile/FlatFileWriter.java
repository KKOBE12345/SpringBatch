package com.qianfeng.itemreaderfile;

import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @program: springbatch
 * @description
 * @author: George
 * @create: 2022-05-02 19:38
 **/
@Component("flatFileItemWriter")
public class FlatFileWriter implements ItemWriter<Customer> {
    @Override
    public void write(List<? extends Customer> list) throws Exception {
        for (Customer customer:list){
            System.out.println(customer);
        }
    }
}
