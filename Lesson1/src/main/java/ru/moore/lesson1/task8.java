package ru.moore.lesson1;

public class task8 {

    public static void main(String[] args) {
        int year = 2100;

        if (year % 100 == 0) {
            if (year % 400 == 0) {
                System.out.println("Год високосный");
            } else {
                System.out.println("Год не високосный");
            }
        } else {
            if (year % 4 == 0) {
                System.out.println("Год високосный");
            } else {
                System.out.println("Год не високосный");
            }
        }
    }

}
