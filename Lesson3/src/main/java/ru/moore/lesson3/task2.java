package ru.moore.lesson3;

import java.util.Random;
import java.util.Scanner;

public class task2 {

    public static void main(String[] args) {
        int secretWord;
        String inputWord;

        String[] words = {"apple", "orange", "lemon", "banana", "apricot", "avocado", "broccoli", "carrot", "cherry", "garlic", "grape", "melon", "leak", "kiwi", "mango", "mushroom", "nut", "olive", "pea", "peanut", "pear", "pepper", "pineapple", "pumpkin", "potato"};

        Random random = new Random();
        Scanner scanner = new Scanner(System.in);

        secretWord = random.nextInt(25);
        System.out.println(words[secretWord]);

        String outputWord = "";
        while (!outputWord.equals(words[secretWord])) {
            outputWord = "";
            System.out.println("Угадайте слово.");
            inputWord = scanner.nextLine();

            for (int i = 0; i < inputWord.length(); i++) {
                boolean search = false;
                for (int x = 0; x < words.length; x++) {
                    if (i + 1 > words[x].length()) {
                        continue;
                    }
                    if (inputWord.charAt(i) == words[x].charAt(i)) {
                        search = true;
                        break;
                    }
                }

                if (search) {
                    outputWord += String.valueOf(inputWord.charAt(i));
                } else {
                    outputWord += "#";
                }
            }

            if (!inputWord.equals(words[secretWord])) {
                for (int i = outputWord.length(); i < 15; i++) {
                    outputWord += "#";
                }
            }

            System.out.println(outputWord);
        }
    }

}
