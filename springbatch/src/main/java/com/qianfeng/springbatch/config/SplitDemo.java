package com.qianfeng.springbatch.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.builder.FlowBuilder;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.task.SimpleAsyncTaskExecutor;

/**
 * @program: springbatch
 * @description
 * @author: George
 * @create: 2022-05-02 10:54
 **/
//@Configuration
//@EnableBatchProcessing
public class SplitDemo {

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
     * @Description: 创建Flow对象,指明F1ow对象包含哪些step
     * @Param: []
     * @Return: org.springframework.batch.core.job.flow.Flow
     */
    @Bean
    public Flow splitDemoFlow1(){
        return new FlowBuilder<Flow>("splitDemoFlow1")
                .start(splitDemoStep1())
                .build();
    }

    @Bean
    public Flow splitDemoFlow2(){
        return new FlowBuilder<Flow>("splitDemoFlow2")
                .start(splitDemoStep2())
                .next(splitDemoStep3())
                .build();
    }

    /**
     * @Description: fowDemoJob
     * @Param: []
     * @Return: org.springframework.batch.core.Job
     */
    @Bean
    public Job splitDemoJob(){
        return jobBuilderFactory.get("splitDemoJob")
                .start(splitDemoFlow1())
                .split(new SimpleAsyncTaskExecutor())
                .add(splitDemoFlow2())
                .end()
                .build();

    }

    @Bean
    public Step splitDemoStep1(){
        return stepBuilderFactory.get("splitDemoStep1")
                .tasklet(new Tasklet() {
                    @Override
                    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
                        System.out.println("splitDemoStep1!");
                        return RepeatStatus.FINISHED;
                    }
                }).build();
    }

    @Bean
    public Step splitDemoStep2(){
        return stepBuilderFactory.get("splitDemoStep2")
                .tasklet(new Tasklet() {
                    @Override
                    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
                        System.out.println("splitDemoStep2!");
                        return RepeatStatus.FINISHED;
                    }
                }).build();
    }

    @Bean
    public Step splitDemoStep3(){
        return stepBuilderFactory.get("splitDemoStep3")
                .tasklet(new Tasklet() {
                    @Override
                    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
                        System.out.println("splitDemoStep3!");
                        return RepeatStatus.FINISHED;
                    }
                }).build();
    }
}

