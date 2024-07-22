package com.nicmora.springbatchdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class SpringBatchDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBatchDemoApplication.class, args);
	}

}
