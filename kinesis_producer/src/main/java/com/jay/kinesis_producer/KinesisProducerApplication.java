package com.jay.kinesis_producer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication
@ComponentScan("com.jay.kinesis_producer.kinesis")
public class KinesisProducerApplication extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(KinesisProducerApplication.class);
	}

	public static void main(String[] args) {
		SpringApplication.run(KinesisProducerApplication.class, args);
	}

}
