package com.welltech;

import com.welltech.common.QuartzManager;
import com.welltech.task.ShutdownAlarmJob;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.Date;

@SpringBootApplication
@ComponentScan(basePackages ="com.welltech")
@EnableScheduling
@EnableAsync
public class WaterAffairApplication {

	private static ApplicationContext context;

	public static ApplicationContext getContext(){
		return context;
	}

	public static void main(String[] args) throws Exception {
		context = SpringApplication.run(WaterAffairApplication.class, args);
	}
}
