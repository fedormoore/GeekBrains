package ru.moore.lesson2;

public class task1 {

    public static void main(String[] args) {
        int[] arr = {0, 0, 1, 1, 0, 1, 0, 0, 1, 1, 0};

        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == 1) {
                arr[i] = 0;
            } else {
                arr[i] = 1;
            }
        }

        for (int value : arr) {
            System.out.print(value + " ");
        }
    }

}
