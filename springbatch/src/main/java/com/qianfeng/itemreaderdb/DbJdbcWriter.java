package com.qianfeng.itemreaderdb;

import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @program: springbatch
 * @description
 * @author: George
 * @create: 2022-05-02 17:50
 **/

@Component("dbJdbcWriter")
public class DbJdbcWriter implements ItemWriter<User> {
    @Override
    public void write(List<? extends User> list) throws Exception {
        for (User user : list) {
            System.out.println(user);
        }
    }
}
