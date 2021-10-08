package com.marchsoft.secondkill;

import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.marchsoft.secondkill.api.mapper")
public class SecondkillApplication {

    public static void main(String[] args) {
        SpringApplication.run(SecondkillApplication.class, args);
    }

}
