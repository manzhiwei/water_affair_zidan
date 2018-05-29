package com.welltech.task;

import com.welltech.WaterAffairApplication;
import com.welltech.service.AlarmService;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import java.util.Date;

/**
 * 设备连接状态由正常到中断报警任务
 */
public class ShutdownAlarmJob implements Job {

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        JobDetail jobDetail = context.getJobDetail();
        JobDataMap map = jobDetail.getJobDataMap();
        //获取站点
        Integer stationId = map.getIntegerFromString("stationId");
        ApplicationContext applicationContext = WaterAffairApplication.getContext();
        AlarmService alarmService = applicationContext.getBean(AlarmService.class);
        //保存报警
        alarmService.saveShutdownAlarm(stationId);
    }
}
