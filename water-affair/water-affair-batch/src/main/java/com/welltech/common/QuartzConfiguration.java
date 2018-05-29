package com.welltech.common;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

@Configuration
public class QuartzConfiguration {

    /**
     * 注册调度工厂
     * @return
     */
    @Bean(name = "scheduler")
    public SchedulerFactoryBean globalScheduler(){
        SchedulerFactoryBean factoryBean = new SchedulerFactoryBean();
        return factoryBean;
    }
}
