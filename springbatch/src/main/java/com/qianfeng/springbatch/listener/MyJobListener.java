package com.qianfeng.springbatch.listener;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;

/**
 * @program: springbatch
 * @description
 * @author: George
 * @create: 2022-05-02 16:21
 **/
public class MyJobListener implements JobExecutionListener {
    @Override
    public void beforeJob(JobExecution jobExecution) {

        System.out.println(jobExecution.getJobInstance().getJobName()+"before---");

    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        System.out.println(jobExecution.getJobInstance().getJobName()+"after---");
    }
}
