package com.nkd.pos.posapi.service;

import java.util.logging.Logger;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledTasks {

	private Logger logger = Logger.getLogger(getClass().getName());
	
	@Scheduled(cron = "${CronScheduledNotifications}")
	public void generalNotifications() {
		logger.info(" ***running***  ");

	}

	
}
