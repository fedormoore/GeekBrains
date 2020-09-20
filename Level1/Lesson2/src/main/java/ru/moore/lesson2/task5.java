package ru.moore.lesson2;

public class task5 {

    public static void main(String[] args) {
        int[] arr = {5, 8, 10, 15, 0, 11, 14, 20};

        int max = arr[0];
        int min = arr[0];

        for (int i = 1; i < arr.length; i++) {
            if (max < arr[i]) {
                max = arr[i];
            }
            if (min > arr[i]) {
                min = arr[i];
            }
        }

        System.out.println("max value = " + max);
        System.out.println("min value = " + min);
    }

}
