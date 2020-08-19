package ru.moore.lesson1;

public class task4 {

    public static void main(String[] args) {
        System.out.println(condition(5, 9));
    }

    public static boolean condition(int x, int y) {
        int c = x + y;

        if (c >= 10) {
            if (c <= 20) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }
}
