package com.qianfeng.retry;

/**
 * @program: springbatch
 * @description
 * @author: George
 * @create: 2022-05-03 15:27
 **/
public class CustomRetryException extends Exception {
    public CustomRetryException(){
        super();
    }
    public CustomRetryException(String msg){
        super(msg);
    }
}
