package com.sazzler.ecommerce.sazzler_productservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

import javax.sql.DataSource;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class SazzlerProductServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(SazzlerProductServiceApplication.class, args);
    }

}
