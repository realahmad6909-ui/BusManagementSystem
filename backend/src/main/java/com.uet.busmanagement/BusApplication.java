package com.uet.busmanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication 
public class BusApplication {

    public static void main(String[] args) {

        System.setProperty("spring.datasource.url", "jdbc:mysql://localhost:3306/uet_bus_management");
        System.setProperty("spring.datasource.username", "root");
        System.setProperty("spring.datasource.password", "iamahmad");
        System.setProperty("spring.datasource.driver-class-name", "com.mysql.cj.jdbc.Driver");

        SpringApplication.run(BusApplication.class, args);
    }
}