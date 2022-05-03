package com.qianfeng.errorhandle;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

/**
 * @program: springbatch
 * @description
 * @author: George
 * @create: 2022-05-03 14:52
 **/
@Configuration
public class ErrorDemo {
    @Autowired
    private JobBuilderFactory jobBuilderFactory;
    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job errorDemoJob(){
        return jobBuilderFactory.get("errorDemoJob")
                .start(errorStep1())
                .next(errorStep2())
                .build();
    }

    @Bean
    public Step errorStep2() {
        return stepBuilderFactory.get("errorStep2")
                .tasklet(errorHandling())
                .build();
    }

    @Bean
    public Step errorStep1() {
        return stepBuilderFactory.get("errorStep1")
                .tasklet(errorHandling())
                .build();
    }

    @Bean
    @StepScope
    public Tasklet errorHandling() {
        return new Tasklet() {
            @Override
            public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {
                Map<String,Object> stepExecutionContext=chunkContext.getStepContext().getStepExecutionContext();

                if (stepExecutionContext.containsKey("kobe")) {
                    System.out.println("二阶段的革命会胜利的！！");
                    return RepeatStatus.FINISHED;
                }
                else {
                    System.out.println("一阶段的革命注定会失败");
                    chunkContext.getStepContext().getStepExecution().getExecutionContext().put("kobe", 24);
                    throw new RuntimeException("error...........===========");
                }

            }
        };
    }


}
