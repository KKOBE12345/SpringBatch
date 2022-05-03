package com.qianfeng.ErrorSkip;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//在读取  和 写入的操作之间 可以进行多个数据操作
@SpringBootApplication
@EnableBatchProcessing
public class SpringbatchApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbatchApplication.class, args);
	}

}
