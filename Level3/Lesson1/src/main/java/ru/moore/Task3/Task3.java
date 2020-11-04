package ru.moore.Task3;

public class Task3 {

    public static void main(String[] args) {
        Apple apple = new Apple();
        Orange orange = new Orange();

        Box<Apple> boxApple1 = new Box();
        Box<Apple> boxApple2 = new Box();
        Box<Orange> boxOrange = new Box();

        for (int i = 0; i < 3; i++) {
            boxApple1.addFruit(new Apple());
        }
        for (int i = 0; i < 4; i++) {
            boxApple2.addFruit(new Apple());
        }

        for (int i = 0; i < 5; i++) {
            boxOrange.addFruit(new Orange());
        }

        System.out.println("Вес коробки с яблоками: " + boxApple1.getWeight());
        System.out.println("Вес коробки с апельсинами: " + boxOrange.getWeight());

        System.out.println("Сравниваем вес коробки с яблоками и апельсинами: " + boxApple1.compare(boxOrange));

        boxApple1.moveAt(boxApple2);
        System.out.println("Вес коробки с яблоками: " + boxApple2.getWeight());
    }

}
