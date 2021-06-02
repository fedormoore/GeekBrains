package ru.moore.lesson3.product.services;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class AppProductAspect {

    @Before("execution(public void ru.moore.lesson3.product.services.ProductService.getProductById())") // pointcut expression
    public void beforeGetProductByIdDAOClass() {
        System.out.println("AOP: получить продукт по id");
    }

    @Before("execution(public void ru.moore.lesson3.product.services.ProductService.findAll())") // pointcut expression
    public void beforeFindAllDAOClass() {
        System.out.println("AOP: получить все продукты");
    }

    @Before("execution(public void ru.moore.lesson3.product.services.ProductService.saveNewProduct())") // pointcut expression
    public void beforeSaveNewProductDAOClass() {
        System.out.println("AOP: сохранили новый продукт");
    }

}
