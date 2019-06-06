package com.leyou;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer //表示EurekaServer注册中心
public class LyRegistry {
    //启动EurekaServer注册中心
    public static void main(String[] args) {
        SpringApplication.run(LyRegistry.class, args);
    }
}
