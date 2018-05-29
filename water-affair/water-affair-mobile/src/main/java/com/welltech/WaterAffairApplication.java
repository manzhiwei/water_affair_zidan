package com.welltech;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages ="com.welltech")
public class WaterAffairApplication {
	public static void main(String[] args) {
		SpringApplication.run(WaterAffairApplication.class, args);
	}
}
