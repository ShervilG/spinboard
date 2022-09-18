package com.shervilg.spinboard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableConfigurationProperties
@SpringBootApplication
public class SpinboardApplication {
	public static void main(String[] args) {
		SpringApplication.run(SpinboardApplication.class, args);
	}
}
