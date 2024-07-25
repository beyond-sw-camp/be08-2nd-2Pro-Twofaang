package com.beyond.twopercent.twofaang;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@MapperScan(basePackages = "com.beyond.twopercent.twofaang.inquiry.mapper")
public class TwofaangApplication {

    public static void main(String[] args) {
        SpringApplication.run(TwofaangApplication.class, args);
    }

}