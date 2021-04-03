package ru.moore.lesson8.exeptions;

public class ThereIsNoValuesInResponse extends RuntimeException {
    public ThereIsNoValuesInResponse(String message) {
        super(message);
    }
}
