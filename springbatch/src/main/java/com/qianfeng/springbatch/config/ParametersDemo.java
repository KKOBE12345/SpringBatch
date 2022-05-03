package com.qianfeng.springbatch.config;

import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;

import java.util.Map;

/**
 * @program: springbatch
 * @description
 * @author: George
 * @create: 2022-05-02 16:45
 **/
//@Configuration
public class ParametersDemo implements StepExecutionListener {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;
    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    private Map<String,JobParameter> parameters;

    @Bean
    public Job parameterJob(){
        return jobBuilderFactory.get("parameterJob")
                .start(parameterStep())
                .build();
    }

    //job执行step  因此我们只需要给step传递参数
    //如何给step传递参数
    //我们可以通过监听的方式 step级别的监听来传递数据
    @Bean
    public Step parameterStep() {
        return stepBuilderFactory.get("parameterStep")
                .listener(this)
                .tasklet(new Tasklet() {
                    @Override
                    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
                        //输出接收的参数的值

                        System.out.println("参数是：：：："+parameters.get("info"));
                        return RepeatStatus.FINISHED;
                    }
                }).build();
    }

    @Override
    public void beforeStep(StepExecution stepExecution) {
        parameters=stepExecution.getJobParameters().getParameters();
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        return null;
    }
}
