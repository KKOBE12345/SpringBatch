package com.qianfeng.retry;

import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @program: springbatch
 * @description
 * @author: George
 * @create: 2022-05-03 15:16
 **/
@Component("retryItemWriter")
public class RetryItemWriter implements ItemWriter<String> {

    @Override
    public void write(List<? extends String> list) throws Exception {
        for (String s : list) {
            System.out.println(s);
        }
    }
}
