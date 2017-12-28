package com.wasu.es;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

@SpringBootApplication
@MapperScan("com.wasu.es.mapper")
public class StatWebApplication{
    public static void main(String[] args) {
        SpringApplication.run(StatWebApplication.class, args);
    }
}
