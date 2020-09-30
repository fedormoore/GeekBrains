package ru.moore;

public class MyArraySizeException extends RuntimeException{

    public MyArraySizeException() {
        super("Массив привышает доступистимый размер!");
    }
}
