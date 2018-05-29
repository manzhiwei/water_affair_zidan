package com.welltech.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.welltech.dao.AlarmDao;
import com.welltech.entity.WtDataRaw;

@Service
public class TaskHandleService {
	
	@Autowired
	private AlarmDao alarmDao;
	
	@Autowired
	private AlarmService alarmService;
	
	@Async
	public void doTask(Date taskTime){
		List<WtDataRaw> datas = alarmDao.findWtDataRawByReceiveTime(taskTime);
		if(datas != null){
			for(WtDataRaw data : datas){
				alarmService.doAlarm(data);
			}
		}
	}
}
