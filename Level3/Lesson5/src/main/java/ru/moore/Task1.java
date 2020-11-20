package ru.moore;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;

public class Task1 {

    public static final int CARS_COUNT = 4;

    public static void main(String[] args) {

        CyclicBarrier cyclicBarrier = new CyclicBarrier(4);

        CountDownLatch finishLatch = new CountDownLatch(4);
        CountDownLatch startLatch = new CountDownLatch(4);

        System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Подготовка!!!");
        Race race = new Race(new Road(60), new Tunnel(), new Road(40));
        Car[] cars = new Car[CARS_COUNT];
        for (int i = 0; i < cars.length; i++) {
            cars[i] = new Car(race, 20 + (int) (Math.random() * 10), cyclicBarrier, finishLatch, startLatch);
        }
        for (int i = 0; i < cars.length; i++) {
            new Thread(cars[i]).start();
        }
        try {
            startLatch.await();
            System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Гонка началась!!!");
            finishLatch.await();
            System.out.println("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Гонка закончилась!!!");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}

