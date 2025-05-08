package com.demo.gradle.quartz;

import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QuartzConfig {

  private static final String JOB_GROUP_NAME = "group";

  @Bean
  public JobDetail helloJobDetail() {
    return JobBuilder.newJob(HelloJob.class)
        .withIdentity("helloJob", JOB_GROUP_NAME)
        .usingJobData("msg", "Hello Quartz")
        // 即使没有trigger也保存这个jobDetail
        .storeDurably()
        .build();
  }

  @Bean
  public Trigger helloJobTrigger() {
    // 每5分钟执行一次
    CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule("0 0 0 * * ?");
    return TriggerBuilder.newTrigger()
        .forJob(helloJobDetail())
        .withIdentity("helloJobTrigger", JOB_GROUP_NAME)
        .withSchedule(cronScheduleBuilder)
        .build();
  }
}
