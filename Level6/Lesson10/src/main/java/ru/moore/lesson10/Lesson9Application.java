package ru.moore.lesson10;

<<<<<<< HEAD
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Lesson9Application {

=======
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

@SpringBootApplication
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class Lesson9Application {

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

>>>>>>> Level6Lesson10
    public static void main(String[] args) {
        SpringApplication.run(Lesson9Application.class, args);
    }

}
