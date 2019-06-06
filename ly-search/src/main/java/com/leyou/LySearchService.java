package com.leyou;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class LySearchService {

    public static void main(String[] args) {
        SpringApplication.run(LySearchService.class, args);
    }
}