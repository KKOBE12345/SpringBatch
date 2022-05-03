package com.qianfeng.springbatch.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.flow.JobExecutionDecider;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;

/**
 * @program: springbatch
 * @description
 * @author: George
 * @create: 2022-05-02 11:17
 **/
//@Configuration
//@EnableBatchProcessing
public class DeciderDemo {
    /**
     * @Description: 注入创建任务对象的对象
     * @Param:
     * @Return:
     */
    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    /**
     * @Description: //任务的执行由Step决定,注入创建Step对象的对象
     * @Param:
     * @Return:
     */
    @Autowired
    private StepBuilderFactory stepBuilderFactory;


    /**
     * @Description: myDecider 决策器
     * @Param: []
     * @Return: org.springframework.batch.core.job.flow.JobExecutionDecider
     */
    @Bean
    public JobExecutionDecider myDecider(){
        return new MyDecider();
    }

    @Bean
    public Job deciderDemoJob(){
        return jobBuilderFactory.get("deciderDemoJob")
                .start(deciderDemoStep1())
                .next(myDecider())
                .from(myDecider()).on("even偶数").to(deciderDemoStep2())
                .from(myDecider())
                .on("odd奇数").to(deciderDemoStep3())
                .from(deciderDemoStep3())
                //无论返回什么值 都返回到决策器
                .on("*").to(myDecider())
                .end()
                .build();
    }



    @Bean
    public Step deciderDemoStep1(){
        return stepBuilderFactory.get("deciderDemoStep1")
                .tasklet(new Tasklet() {
                    @Override
                    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
                        System.out.println("deciderDemoStep1!");
                        return RepeatStatus.FINISHED;
                    }
                }).build();
    }

    @Bean
    public Step deciderDemoStep2(){
        return stepBuilderFactory.get("deciderDemoStep2")
                .tasklet(new Tasklet() {
                    @Override
                    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
                        System.out.println("deciderDemoStep2 偶数!");
                        return RepeatStatus.FINISHED;
                    }
                }).build();
    }

    @Bean
    public Step deciderDemoStep3(){
        return stepBuilderFactory.get("deciderDemoStep3")
                .tasklet(new Tasklet() {
                    @Override
                    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
                        System.out.println("deciderDemoStep3 奇数!");
                        return RepeatStatus.FINISHED;
                    }
                }).build();
    }
}
