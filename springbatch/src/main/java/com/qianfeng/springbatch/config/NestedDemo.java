package com.qianfeng.springbatch.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.JobStepBuilder;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.PlatformTransactionManager;

/**
 * @program: springbatch
 * @description
 * @author: George
 * @create: 2022-05-02 11:34
 **/
//@Configuration
public class NestedDemo {
    @Autowired
    private JobBuilderFactory jobBuilderFactory;
    @Autowired
    private StepBuilderFactory stepBuilderFactory;
    @Autowired
    private JobLauncher launcher;
    @Autowired
    private Job childJobOne;
    @Autowired
    private Job childJobTwo;





    @Bean
    public Job parentJob(JobRepository jobRepository, PlatformTransactionManager transactionManager){
        return jobBuilderFactory.get("parentJobs")
                .start(childJob1(jobRepository,transactionManager))
                .next(childJob2(jobRepository,transactionManager))
                .build();
    }

    /**
     * @Description: childJob2返回Job类型的Step，特殊Step
     * @Param: []
     * @Return: org.springframework.batch.core.Step
     */
    private Step childJob2(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new JobStepBuilder(new StepBuilder("childJob2"))
                .job(childJobTwo)
                .launcher(launcher)
                .repository(jobRepository)
                .transactionManager(transactionManager)
                .build();

    }

    private Step childJob1(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new JobStepBuilder(new StepBuilder("childJob1"))
                .job(childJobOne)
                .launcher(launcher)
                .repository(jobRepository)
                .transactionManager(transactionManager)
                .build();
    }

}
