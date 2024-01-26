package com.woo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.woo.mapper")
public class WooBlogAdminApplication {
    public static void main(String[] args) {
        SpringApplication.run(WooBlogAdminApplication.class, args);
    }
}
