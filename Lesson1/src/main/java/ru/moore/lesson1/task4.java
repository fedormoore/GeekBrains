package ru.moore.lesson1;

public class task4 {

    public static void main(String[] args) {
        System.out.println(condition(5, 5));
    }

    public static boolean condition(int x, int y) {
        int c = x + y;

        if (c >= 10 && c <= 20) {
            return true;
        } else {
            return false;
        }
    }
}
