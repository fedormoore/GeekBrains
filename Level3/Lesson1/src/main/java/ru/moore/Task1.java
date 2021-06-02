package ru.moore;

import java.util.Arrays;

public class Task1 {

    public static void main(String[] args) {
        Integer[] arrayInt = {1, 2};
        String[] arrayStr = {"1", "2"};

        change(arrayInt);
        System.out.println(Arrays.toString(arrayInt));

        change(arrayStr);
        System.out.println(Arrays.toString(arrayStr));
    }

    public static <T> void change(T[] array) {
        T temp = array[0];
        array[0] = array[1];
        array[1] = temp;
    }

}
