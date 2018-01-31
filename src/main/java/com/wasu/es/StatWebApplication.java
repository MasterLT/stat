package com.wasu.es;

import com.github.pagehelper.PageInterceptor;
import com.wasu.es.model.StatConfig;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
@MapperScan("com.wasu.es.mapper")
public class StatWebApplication {

    public static void main(String[] args) throws InstantiationException, IllegalAccessException {
        SpringApplication.run(StatWebApplication.class, args);
    }
}
