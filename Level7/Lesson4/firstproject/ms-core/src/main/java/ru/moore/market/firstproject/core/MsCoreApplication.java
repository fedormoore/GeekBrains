package ru.moore.market.firstproject.core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "ru.moore.market.firstproject")
public class MsCoreApplication {

    public static void main(String[] args) {
        SpringApplication.run(MsCoreApplication.class, args);
    }

}
