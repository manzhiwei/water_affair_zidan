package com.welltech.task;

import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.welltech.service.TaskHandleService;

/**
 * 心跳任务
 * @author WangXin
 *
 */
@Component
public class HeartbeatTask {
	
	@Autowired
	private TaskHandleService handleService;
	
	@Scheduled(cron="0/1 * * * * *")
	public void task(){
		Calendar taskTime = Calendar.getInstance();
		taskTime.add(Calendar.SECOND, -5);//延迟5秒读库
		taskTime.set(Calendar.MILLISECOND, 0);
		handleService.doTask(taskTime.getTime());
	}
}
