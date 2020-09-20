package ru.moore.lesson3;

import java.util.Random;
import java.util.Scanner;

public class task1 {

    public static void main(String[] args) {
        Random random = new Random();
        Scanner scanner = new Scanner(System.in);

        boolean again = true;
        int secretDig;
        int countAtt;

        while (again) {
            secretDig = random.nextInt(10);
            System.out.println(secretDig);

            countAtt = 1;

            System.out.println("Угадайте число от 0 до 9");
            int inputDig = scanner.nextInt();

            while (countAtt < 3) {
                if (inputDig == secretDig) {
                    System.out.printf("Поздравляем! Вы угадали число %d", secretDig);
                    System.out.println();
                    break;
                } else if (inputDig > secretDig) {
                    System.out.println("Загаданное число меньше.");
                } else {
                    System.out.println("Загаданное число больше.");
                }
                inputDig = scanner.nextInt();
                countAtt++;
            }

            if (countAtt == 3) {
                System.out.println("К сожалению вы проиграли.");
            }

            System.out.println("Повторить игру еще раз? 1 – да / 0 – нет");
            int answer = scanner.nextInt();

            while (answer != 0 && answer != 1) {
                System.out.println("Введите 1 или 0! 1 – да / 0 – нет");
                answer = scanner.nextInt();
            }

            if (answer == 0) {
                again = false;
            }
        }
    }

}
