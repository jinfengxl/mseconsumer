package com.xl;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;
import java.util.Collections;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class StartApplication {


    @LoadBalanced
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }



    public static void main(String[] args) {

        SpringApplication.run(StartApplication.class, args);

    }
}
