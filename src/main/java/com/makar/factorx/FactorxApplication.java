package com.makar.factorx;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseAutoConfiguration;


@SpringBootApplication(exclude = {LiquibaseAutoConfiguration.class})
public class FactorxApplication {

    public static void main(String[] args) {
        SpringApplication.run(FactorxApplication.class, args);
    }

}
