package com.welltech.service;

import com.welltech.common.QuartzManager;
import com.welltech.dao.AlarmDao;
import com.welltech.entity.WtDataRaw;
import com.welltech.entity.WtStation;
import com.welltech.task.ShutdownAlarmJob;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.Date;

/**
 * 管理报警任务
 */
@Component
public class AlarmManageService {

    /** 任务组名 */
    public static final String JOB_GROUP_NAME = "SHUTDOWN_ALARM_JGN";

    /** 触发器组名 */
    public static final String TRIGGER_GROUP_NAME = "SHUTDOWN_ALARM_TGN";

    /** 任务名前缀 */
    public static final String JOB_NAME_PREFIX = "JB";

    /** 触发器前缀 */
    public static final String TRIGGER_NAME_PREFIX = "TG";

    /** 超时倍数 */
    public static final int OVERTIME_MULTIPLE = 2;

    @Autowired
    private AlarmDao alarmDao;

    @Autowired
    private QuartzManager quartzManager;

    /**
     * 连接时处理
     */
    public void saveShutodownJob(WtStation station, WtDataRaw data) {
        Integer stationId = station.getId();
        Date connectTime = data.getReceiveTime();//调用已保证不为空
        int transferCycle = station.getTransferCycle();

        if(transferCycle > 0){
            //几分钟后算断开
            int overtime =  transferCycle * OVERTIME_MULTIPLE;

            //任务数据
            JobDataMap map = new JobDataMap();
            map.putAsString("stationId", stationId);
            map.putAsString("connectTime", connectTime.getTime());
            map.putAsString("transferCycle", overtime);

            //
            Calendar c = Calendar.getInstance();
            c.setTime(connectTime);
            c.add(Calendar.MINUTE, overtime);
            Date runTime = c.getTime();

            JobDetail jobDetail = quartzManager.getJobDetail(JOB_NAME_PREFIX + stationId, JOB_GROUP_NAME);
            if(jobDetail != null){
                //更新任务
                quartzManager.modifyJob(JOB_NAME_PREFIX + stationId, JOB_GROUP_NAME, TRIGGER_NAME_PREFIX + stationId, TRIGGER_GROUP_NAME,
                        runTime, overtime, map);
            } else{
                //新建任务
                quartzManager.addJob(JOB_NAME_PREFIX + stationId, JOB_GROUP_NAME, TRIGGER_NAME_PREFIX + stationId, TRIGGER_GROUP_NAME,
                        ShutdownAlarmJob.class, runTime, overtime, map);
            }
        } else{
            removeShutdownJob(stationId);
        }
    }

    /**
     * 移除任务
     * @param stationId
     */
    public void removeShutdownJob(Integer stationId){
        quartzManager.removeJob(JOB_NAME_PREFIX + stationId, JOB_GROUP_NAME, TRIGGER_NAME_PREFIX + stationId, TRIGGER_GROUP_NAME);
    }

}
