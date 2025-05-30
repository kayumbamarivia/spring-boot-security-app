package com.me.security.doit.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class CronBean {
    private static final Logger logger = LoggerFactory.getLogger(CronBean.class);
    public CronBean() {
        logger.info("CronBean instantiated");
    }

    @Scheduled(cron = "0/2 * * * * ?")
    public void cronJob() {
        logger.info("Cron job executed at: {}",new java.util.Date());
    }
}