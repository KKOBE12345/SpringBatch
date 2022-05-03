package com.qianfeng.itemreaderfile;

import com.qianfeng.springbatch.listener.MyJobListener;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.bind.BindException;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;



/**
 * @program: springbatch
 * @description
 * @author: George
 * @create: 2022-05-02 19:26
 **/

//@Configuration
public class FileItemReaderDemo {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;
    @Autowired
    private StepBuilderFactory stepBuilderFactory;
    @Autowired
    @Qualifier("flatFileItemWriter")
    private ItemWriter<? super Customer> flatFileItemWriter;

    @Bean
    public Job fileItemReaderDemoJob() {
        return jobBuilderFactory.get("fileItemReaderDemoJob")
                .start(fileItemReaderDemoStep())
                .listener(new MyJobListener())
                .build();
    }

    @Bean
    public Step fileItemReaderDemoStep() {

        return stepBuilderFactory.get("fileItemReaderDemoStep")
                .<Customer,Customer>chunk(10)
                .reader(fileItemReaderDemoReader())
                .writer(flatFileItemWriter)
                .build();
    }



    /**
     * @Description: FlatItemReaderDemoReader 文件读取
     * @Param: []
     * @Return: org.springframework.batch.item.file.FlatFileItemReader<com.example.springbatchdemo.pojo.Customer>
     */
    @Bean
    @StepScope
    public FlatFileItemReader<Customer> fileItemReaderDemoReader() {
        FlatFileItemReader<Customer> reader = new FlatFileItemReader<Customer>();
        reader.setResource(new ClassPathResource("customer.txt"));
        //跳过第一行
        reader.setLinesToSkip(1);
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
