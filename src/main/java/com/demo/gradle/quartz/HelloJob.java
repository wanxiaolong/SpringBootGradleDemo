package com.demo.gradle.quartz;

import java.util.Date;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

@Slf4j
public class HelloJob extends QuartzJobBean {

  @Override
  protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
    // 获取Job参数
    context
        .getJobDetail()
        .getJobDataMap()
        .forEach((k, v) -> log.info("Job参数：key={}，value={}", k, v));
    // 输出当前时间
    log.info("----------------HelloJob执行，当前时间：" + new Date());
  }
}
