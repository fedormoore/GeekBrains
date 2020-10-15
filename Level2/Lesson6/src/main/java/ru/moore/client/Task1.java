package ru.moore.client;

import java.util.Scanner;

public class Task1 {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Network network = new Network();
        network.setDaemon(true);
        network.start();

        while (true) {
            String message = scanner.nextLine();
            network.sendMessage(message);
        }
    }
}
