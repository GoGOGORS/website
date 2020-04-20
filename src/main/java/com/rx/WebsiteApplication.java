package com.rx;

import com.rx.config.GateWayServer;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.Arrays;

/**
 * @author Rxiang
 */
@MapperScan("com.rx.mapper")
@SpringBootApplication
public class WebsiteApplication {

    public static void main(String[] args) {
        ApplicationContext applicationContext = SpringApplication.run(WebsiteApplication.class, args);
        System.out.println("Let's inspect the beans provided by Spring Boot:");

        String[] beanNames = applicationContext.getBeanDefinitionNames();
        Arrays.sort(beanNames);
        for (String beanName : beanNames) {
            System.out.println(beanName);
        }

        GateWayServer.startListenGateWay();
    }

}
