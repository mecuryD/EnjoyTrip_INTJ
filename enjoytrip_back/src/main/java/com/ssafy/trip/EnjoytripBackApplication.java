package com.ssafy.trip;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.ssafy.trip"})
public class EnjoytripBackApplication {

	public static void main(String[] args) {
		SpringApplication.run(EnjoytripBackApplication.class, args);
	}

}
