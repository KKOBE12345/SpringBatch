package com.qianfeng.itemwriter;

import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @program: springbatch
 * @description
 * @author: George
 * @create: 2022-05-03 08:16
 **/

@Component("myWriter")
public class MyWriter implements ItemWriter<String> {
    @Override
    public void write(List<? extends String> list) throws Exception {
        System.out.println(list.size());
        for (String s : list) {

            System.out.println(s);
        }
    }
}
