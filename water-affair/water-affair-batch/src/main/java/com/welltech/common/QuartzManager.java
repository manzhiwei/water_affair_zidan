package com.welltech.common;

import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;

@Component
public class QuartzManager {

    @Autowired
    @Qualifier("scheduler")
    private Scheduler scheduler;

    /**
     * 添加一个定时任务
     * @param jobName
     * @param jobGroupName
     * @param triggerName
     * @param triggerGroupName
     * @param jobClass
     * @param startAt
     * @param intervalMinutes
     */
    public void addJob(String jobName, String jobGroupName, String triggerName, String triggerGroupName, Class jobClass, Date startAt, int intervalMinutes, Map<String, Object> map){
        try {
            // 指定任务名、组及执行类
            JobDetail jobDetail = JobBuilder.newJob(jobClass)
                    .withIdentity(jobName, jobGroupName)
                    .setJobData(new JobDataMap(map))
                    .build();
            // 构建触发器
            TriggerBuilder<Trigger> triggerBuilder = TriggerBuilder.newTrigger();
            triggerBuilder.withIdentity(triggerName, triggerGroupName);
            triggerBuilder.startAt(startAt);
            triggerBuilder.withSchedule(SimpleScheduleBuilder.simpleSchedule().repeatMinutelyForever(intervalMinutes));
            //创建trigger
            SimpleTrigger trigger = (SimpleTrigger) triggerBuilder.build();

            scheduler.scheduleJob(jobDetail, trigger);

            if(!scheduler.isShutdown()){
                //启动
                scheduler.start();
            }

        } catch (SchedulerException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 移除一个任务
     * @param jobName
     * @param jobGroupName
     * @param triggerName
     * @param triggerGroupName
     */
    public void removeJob(String jobName, String jobGroupName, String triggerName, String triggerGroupName){
        try {
            TriggerKey triggerKey = TriggerKey.triggerKey(triggerName, triggerGroupName);

            //停止触发器
            scheduler.pauseTrigger(triggerKey);
            //移除触发器
            scheduler.unscheduleJob(triggerKey);
            //删除任务
            scheduler.deleteJob(JobKey.jobKey(jobName, jobGroupName));

        } catch (SchedulerException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 更新任务时间
     * @param jobName
     * @param jobGroupName
     * @param triggerName
     * @param triggerGroupName
     * @param startAt
     * @param intervalMinutes
     */
    public void modifyJob(String jobName, String jobGroupName, String triggerName, String triggerGroupName, Date startAt, int intervalMinutes, Map<String, Object> map){
        /*try {
            TriggerKey triggerKey = TriggerKey.triggerKey(triggerName, triggerGroupName);
            SimpleTrigger trigger = (SimpleTrigger) scheduler.getTrigger(triggerKey);
            if(trigger != null){
                long oldInterval = trigger.getRepeatInterval();
                long interval = intervalMinutes * 60l * 1000l;
                if(interval != oldInterval){
                    // 构建触发器
                    TriggerBuilder<Trigger> triggerBuilder = TriggerBuilder.newTrigger();
                    triggerBuilder.withIdentity(triggerName, triggerGroupName);
                    triggerBuilder.startAt(startAt);
                    triggerBuilder.withSchedule(SimpleScheduleBuilder.simpleSchedule().repeatMinutelyForever(intervalMinutes));
                    //创建trigger
                    SimpleTrigger trigger = (SimpleTrigger) triggerBuilder.build();
                    scheduler.rescheduleJob(triggerKey, trigger);
                }
            }
        } catch (SchedulerException e) {
            e.printStackTrace();
        }*/
        //使用先删除后创建的方式进行
        try {
            JobDetail jobDetail = scheduler.getJobDetail(JobKey.jobKey(jobName, jobGroupName));
            removeJob(jobName, jobGroupName, triggerName, triggerGroupName);
            addJob(jobName, jobGroupName, triggerName, triggerGroupName, jobDetail.getJobClass(), startAt, intervalMinutes, map);
        } catch (SchedulerException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * 启动所有任务
     */
    public void startJobs(){
        try {
            scheduler.start();
        } catch (SchedulerException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 关闭所有任务
     */
    public void stopJobs(){
        try {
            if(!scheduler.isShutdown()){
                scheduler.shutdown();
            }
        } catch (SchedulerException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 查询任务
     * @param jobName
     * @param jobGroupName
     * @return
     */
    public JobDetail getJobDetail(String jobName, String jobGroupName){
        JobDetail jobDetail = null;
        try {
            jobDetail = scheduler.getJobDetail(JobKey.jobKey(jobName, jobGroupName));
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        return jobDetail;
    }

}
