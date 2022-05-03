package com.qianfeng.springbatch.config;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.job.flow.FlowExecutionStatus;
import org.springframework.batch.core.job.flow.JobExecutionDecider;

/**
 * @program: springbatch
 * @description
 * @author: George
 * @create: 2022-05-02 11:21
 **/

public class MyDecider implements JobExecutionDecider {
    private int count;


    /**
     * @Description: decide 决策器，先执行odd奇数
     * @Param: [jobExecution, stepExecution]
     * @Return: org.springframework.batch.core.job.flow.FlowExecutionStatus
     */
    @Override
    public FlowExecutionStatus decide(JobExecution jobExecution, StepExecution stepExecution) {
        count++;
        if(count%2==0){
            return new FlowExecutionStatus("even偶数");
        }else {
            return new FlowExecutionStatus("odd奇数");
        }
    }
}
