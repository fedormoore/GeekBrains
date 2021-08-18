package ru.moore.lesson3.task1;

public class PingPong {

    public static void main(String[] args) {

        final int numberOfPlays = 10;

        new Thread(new Runnable() {
            @Override
            public void run() {
                play("ping", numberOfPlays);
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                play("pong", numberOfPlays);
            }
        }).start();

    }

    private static synchronized void play(String name, int numberOfPlays) {

        while (true) {
            System.out.println(name);
            try {
                PingPong.class.notify();
                if (--numberOfPlays == 0) {
                    break;
                } else {
                    PingPong.class.wait();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

}
