package com.qianfeng.ErrorSkip;

import org.springframework.batch.core.SkipListener;
import org.springframework.stereotype.Component;

/**
 * @program: springbatch
 * @description
 * @author: George
 * @create: 2022-05-03 16:04
 **/
@Component
public class MySkipListener implements SkipListener<String,String> {
    @Override
    public void onSkipInRead(Throwable throwable) {

    }

    @Override
    public void onSkipInWrite(String s, Throwable throwable) {

    }

    @Override
    public void onSkipInProcess(String s, Throwable throwable) {

        System.out.println(s+"发生了异常"+"+++++++++++++++");

    }
}
