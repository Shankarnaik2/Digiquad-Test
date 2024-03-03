package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@ComponentScan(basePackages={" com.example.demo.conroller" + "com.example.demo.service"+ "com.example.demo.service"+ "com.example.demo.service"})   
public class DigiquadTest1Application {
    public static void main(String[] args) {
        SpringApplication.run(DigiquadTest1Application.class, args);
    }
}

