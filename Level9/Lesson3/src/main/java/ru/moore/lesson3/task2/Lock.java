package ru.moore.lesson3.task2;

import java.util.concurrent.locks.ReentrantLock;

public class Lock {

    public static void main(String[] args) {
        CommonResource commonResource = new CommonResource();
        ReentrantLock locker = new ReentrantLock();
        for (int i = 1; i < 6; i++) {
            Thread t = new Thread(new CountThread(commonResource, locker));
            t.setName("Thread " + i);
            t.start();
        }
    }

}

class CommonResource {
    int x = 0;
}

class CountThread implements Runnable {

    CommonResource commonResource;
    ReentrantLock locker;

    CountThread(CommonResource commonResource, ReentrantLock locker) {
        this.commonResource = commonResource;
        this.locker = locker;
    }

    public void run() {
        try {
            locker.lock();
            for (int i = 1; i < 5; i++) {
                System.out.printf("%s %d \n", Thread.currentThread().getName(), i);
                Thread.sleep(100);
            }
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        } finally {
            locker.unlock();
        }
    }
}
