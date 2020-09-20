package ru.moore.lesson2;

public class task6 {

    public static void main(String[] args) {
        int[] arrTrue = {1,1,1,2,1};
        int[] arrFalse = {1,1,1,3,1};

        System.out.println(checkBalance(arrTrue));
        System.out.println(checkBalance(arrFalse));
    }

    private static boolean checkBalance(int[] arr) {
        int minusRight = 1;
        int left = arr[0];
        int right = arr[arr.length - 1];

        for (int i = 1; i < arr.length - minusRight; i++) {
            if (left < right) {
                left += arr[i];
            } else {
                left += arr[i];
                right += arr[arr.length - i - 1];
                minusRight++;
            }
        }
        if (left == right) {
            return true;
        } else {
            return false;
        }
    }

}
