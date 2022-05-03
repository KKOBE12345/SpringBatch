package com.qianfeng.itemwriterxml;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//从数据库中读取数据 写到xml中  出了点问题 每次都读取失败  很奇怪啊！！！！！！
@SpringBootApplication
@EnableBatchProcessing
public class SpringbatchApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbatchApplication.class, args);
	}

}
