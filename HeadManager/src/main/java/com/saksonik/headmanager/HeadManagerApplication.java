package com.saksonik.headmanager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class HeadManagerApplication {

    public static void main(String[] args) {
        SpringApplication.run(HeadManagerApplication.class, args);
    }

}
