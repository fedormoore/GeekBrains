package ru.moore.lesson6;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import ru.moore.lesson6.services.CustomerService;
import ru.moore.lesson6.services.ProductService;

@SpringBootApplication
public class Lesson6Application {

    public static void main(String[] args) {

        ConfigurableApplicationContext context = SpringApplication.run(Lesson6Application.class, args);

        ProductService productService = context.getBean(ProductService.class);
        CustomerService customerService = context.getBean(CustomerService.class);

        //customerService.getCustomerById(1L);
        productService.getListCustomerProduct(1L);
    }

}
