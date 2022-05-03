package com.qianfeng.itemreadermulti;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.MultiResourceItemReader;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.bind.BindException;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

/**
 * @program: springbatch
 * @description
 * @author: George
 * @create: 2022-05-02 20:29
 **/
//@Configuration
public class MultiFileItemReaderDemo {
    @Autowired
    private JobBuilderFactory jobBuilderFactory;
    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Value("classpath:/file*.txt")
    private Resource[] fileResoueces;
    @Autowired
    @Qualifier("multiFileWriter")
    private ItemWriter<? super Customer> multiFileWriter;


    /**
     * @Description: multiFileItemWriterDemoJob
     * @Param: []
     * @Return: org.springframework.batch.core.Job
     */
    @Bean
    public Job multiFileItemWriterDemoJob() {
        return jobBuilderFactory.get("multiFileItemWriterDemoJob")
                .start(multiFileItemWriterDemoStep())
//                .listener(new MyJobListener())
                .build();
    }

    /**
     * @Description: multiFileItemWriterDemoStep
     * @Param: []
     * @Return: org.springframework.batch.core.Step
     */
    @Bean
    public Step multiFileItemWriterDemoStep() {
        return stepBuilderFactory.get("multiFileItemWriterDemoStep")
                .<Customer,Customer>chunk(5)
                .reader(multiFileReader())
                .writer(multiFileWriter)
                //ClassifierCompositeItemWriter没有实现ItemStream
                .build();
    }

    @Bean
    @StepScope
    public MultiResourceItemReader<Customer> multiFileReader() {
        MultiResourceItemReader<Customer> reader=new MultiResourceItemReader<>();
        reader.setDelegate(fileItemReaderDemoReader());

        reader.setResources(fileResoueces);
        return reader;
    }

    @Bean
    @StepScope
    public FlatFileItemReader<Customer> fileItemReaderDemoReader() {
        FlatFileItemReader<Customer> reader = new FlatFileItemReader<Customer>();
        reader.setResource(new ClassPathResource("customer.txt"));
        //跳过第一行
//        reader.setLinesToSkip(1);
        //数据解析
        DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer();
        tokenizer.setNames(new String[]{"id","fistName","lastName","birthday"});
        //把解析出的一个数据映射为Customer对象
        DefaultLineMapper<Customer> mapper = new DefaultLineMapper<>();
        mapper.setLineTokenizer(tokenizer);
        mapper.setFieldSetMapper(new FieldSetMapper<Customer>() {

            /**
             * @Description: mapFieldSet 映射
             * @Param: [fieldSet]
             * @Return: com.example.springbatchdemo.pojo.Customer
             */
            @Override
            public Customer mapFieldSet(FieldSet fieldSet) throws BindException {
                Customer customer = new Customer();
                customer.setId(fieldSet.readLong("id"));
                customer.setFirstName(fieldSet.readString("fistName"));
                customer.setLastName(fieldSet.readString("lastName"));
                customer.setBirthday(fieldSet.readString("birthday"));
                return customer;
            }
        });
        mapper.afterPropertiesSet();
        reader.setLineMapper(mapper);
        return reader;
    }
}
