package ru.moore.lesson7.exeptions;

public class ThereIsNoValuesInResponse extends RuntimeException {
    public ThereIsNoValuesInResponse(String message) {
        super(message);
    }
}
