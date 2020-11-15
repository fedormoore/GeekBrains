package ru.moore;

public class Task1 {

    private static String currentChar = "A";
    static Object mon = new Object();

    public static void main(String[] args) {
        Thread a = new Thread(new Runnable() {
            @Override
            public void run() {
                task("A", "B");
            }
        });
        Thread b = new Thread(new Runnable() {
            @Override
            public void run() {
                task("B", "C");
            }
        });
        Thread c = new Thread(new Runnable() {
            @Override
            public void run() {
                task("C", "A");
            }
        });
        a.start();
        b.start();
        c.start();
    }


    private static void task(String firstLetter, String secondLetter) {
        synchronized (mon) {
            for (int i = 0; i < 5; i++) {
                try {
                    while (!currentChar.equals(firstLetter)) {
                        mon.wait();
                    }
                    System.out.print(currentChar);
                    currentChar = secondLetter;
                    mon.notifyAll();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }
    }

}