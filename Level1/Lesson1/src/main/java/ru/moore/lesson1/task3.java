package ru.moore.lesson1;

public class task3 {

    public static void main(String[] args) {
        System.out.println(calcExpression(0.5f, 1.5f, 2.5f, 5.4f));
    }

    public static float calcExpression(float a, float b, float c, float d) {
        return a * (b + (c / d));
    }
}
