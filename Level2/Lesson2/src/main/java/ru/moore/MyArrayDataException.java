package ru.moore;

public class MyArrayDataException extends RuntimeException{

    private int i;
    private int j;

    public MyArrayDataException(int i, int j){
        super("В ячейки " + i + "-" + j +" тип данных char вместо int!");
    }

}
