package ru.moore.lesson2;

public class task2 {

    public static void main(String[] args) {
        int[] arr = new int[8];
        int x = 0;

        for (int i = 0; i < arr.length; i++) {
            arr[i] = x;
            x += 3;
        }

        for (int value : arr) {
            System.out.print(value + " ");
        }
    }

}
