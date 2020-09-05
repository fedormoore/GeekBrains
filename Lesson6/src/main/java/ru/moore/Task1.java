package ru.moore;

import java.util.Random;

public class Task1 {

    public static void main(String[] args) {
        Random random = new Random();

        Animal dog = new Dog(random.nextInt(500), random.nextInt(50), random.nextInt(5));
        Animal dog1 = new Dog(random.nextInt(500), random.nextInt(50), random.nextInt(5));
        Animal dog2 = new Dog(random.nextInt(500), random.nextInt(50), random.nextInt(5));
        Animal cat = new Cat(random.nextInt(500), random.nextInt(50), random.nextInt(5));
        Animal cat1 = new Cat(random.nextInt(500), random.nextInt(50), random.nextInt(5));
        Animal cat2 = new Cat(random.nextInt(500), random.nextInt(50), random.nextInt(5));

        printInfo(dog, dog1, dog2, cat, cat1, cat2);
    }

    private static void printInfo(Animal... animals) {
        for (Animal instance : animals) {
            instance.canRun(150);
            instance.canJump(25);
            instance.canSwim(3);
            System.out.println("---------------------");
        }
    }

}
