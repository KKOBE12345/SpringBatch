package com.qianfeng.itemreader;

import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;

import java.util.Iterator;
import java.util.List;

/**
 * @program: springbatch
 * @description
 * @author: George
 * @create: 2022-05-02 17:15
 **/
public class MyReader implements ItemReader<String> {
    private Iterator<String> iterator;

    public MyReader(List<String> list) {
         this.iterator = list.iterator();

    }

    @Override
    public String read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        //一个一个的读数据
        if(iterator.hasNext()){
            return this.iterator.next();
        }else {
            return null;
        }

    }
}
