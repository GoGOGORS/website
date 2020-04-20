package com.rx.job;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * RuiXiang
 * 2020-04-20-15:53
 *
 * @author: RuiXiang
 */

@Configuration
@EnableScheduling
public class TestJob {

    @Scheduled(fixedRate = 5000)
    public void test(){
        System.out.println("test");
    }


}
