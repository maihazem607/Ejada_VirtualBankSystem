package com.BFFService.BFFService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class BFFServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(BFFServiceApplication.class, args);
	}

}
