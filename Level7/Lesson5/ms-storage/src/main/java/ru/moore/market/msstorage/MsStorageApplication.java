package ru.moore.market.msproduct;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class MsStorageApplication {

    @Bean
    public ModelMapper modelMapper(){return new ModelMapper();}

    public static void main(String[] args) {
        SpringApplication.run(MsStorageApplication.class, args);
    }

}
