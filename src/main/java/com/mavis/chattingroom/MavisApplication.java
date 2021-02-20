package com.mavis.chattingroom;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.mavis.chattingroom.dao")
public class MavisApplication {

    public static void main(String[] args) {
        SpringApplication.run(MavisApplication.class, args);
    }

}
