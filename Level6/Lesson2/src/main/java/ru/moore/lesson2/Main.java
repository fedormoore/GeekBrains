package ru.moore.lesson2;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.moore.lesson2.services.ProductService;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

        ProductService productService = context.getBean("productService", ProductService.class);

        System.out.println("Список подерживаемых команд:");
        System.out.println("1: получить общее количество товара");
        System.out.println("2: получить среднюю цену");
        System.out.println("3: добавить продукт");
        System.out.println("4: обновить информацию о продукте");
        System.out.println("5: удалить продукт");
        System.out.println("6: найти продукт по id");
        System.out.println("7: вывести список всех продуктов");
        System.out.println("8: выход");
        Scanner sc = new Scanner(System.in);
        String cmd;
        Boolean loop=true;
        while (loop) {
            cmd = sc.nextLine();
            switch (cmd) {
                case "1": {
                    System.out.println(productService.getTotalProduct());
                    break;
                }
                case "2": {
                    System.out.println(productService.getAverageCost());
                    break;
                }
                case "3": {
                    System.out.println("Введите продукт в формате имя:цена");
                    cmd = sc.nextLine();
                    System.out.println(productService.addProduct(cmd));
                    break;
                }
                case "4": {
                    System.out.println("Введите продукт в формате id:имя:цена");
                    cmd = sc.nextLine();
                    System.out.println(productService.updateProduct(cmd));
                    break;
                }
                case "5": {
                    System.out.println("Введите id продука");
                    cmd = sc.nextLine();
                    System.out.println(productService.deleteProduct(cmd));
                    break;
                }
                case "6": {
                    System.out.println("Введите имя продука");
                    cmd = sc.nextLine();
                    System.out.println(productService.findProduct(cmd));
                    break;
                }
                case "7": {
                    productService.findAllProduct();
                    break;
                }
                case "8": {
                    loop=false;
                }
            }
        }

        context.close();
    }

}
