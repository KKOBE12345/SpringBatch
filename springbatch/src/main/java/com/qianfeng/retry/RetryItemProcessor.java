package com.qianfeng.retry;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

/**
 * @program: springbatch
 * @description
 * @author: George
 * @create: 2022-05-03 15:19
 **/
@Component
public class RetryItemProcessor implements ItemProcessor<String,String> {

    private int attemptCount=0;
    @Override
    public String process(String item) throws Exception {
        System.out.println("processor item "+item);
        if (item.equalsIgnoreCase("26")) {
            attemptCount++;
            if (attemptCount>=3) {
                System.out.println("Retried "+attemptCount+" times success");
                return String.valueOf(Integer.valueOf(item)*-1);
            }else{
                System.out.println("Process the "+attemptCount+" times fail");
                throw new CustomRetryException("尝试了很多次 但是依然失败了");
            }

        }
        else{
            return String.valueOf(Integer.valueOf(item)*-1);
        }

    }
}
