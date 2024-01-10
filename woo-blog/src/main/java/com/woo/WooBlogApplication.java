package com.woo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.woo.mapper")
public class WooBlogApplication {
    public static void main(String[] args) {
        SpringApplication.run(WooBlogApplication.class,args);
    }
}
