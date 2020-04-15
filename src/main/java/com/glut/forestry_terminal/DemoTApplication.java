package com.glut.forestry_terminal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class DemoTApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoTApplication.class, args);
    }
}
