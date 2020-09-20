package ru.moore.lesson2;

public class task7 {

    public static void main(String[] args) {
        int[] arr = {3, 5, 6, 1};
        int n = -2;

        shiftArr(arr, n);
    }

    private static void shiftArr(int[] arr, int n) {
        if (n < 0) {
            n = arr.length + n;
        }

        for (int i = 0; i < n; i++) {
            int val = arr[arr.length - 1];
            shiftArr2(arr, 1, arr[0]);
            arr[0] = val;
        }

        for (int value : arr) {
            System.out.print(value + " ");
        }
    }

    private static void shiftArr2(int[] arr, int x, int value) {
        if (x < arr.length) {
            int val = arr[x];
            shiftArr2(arr, x + 1, val);
            arr[x] = value;
        }
    }

}
