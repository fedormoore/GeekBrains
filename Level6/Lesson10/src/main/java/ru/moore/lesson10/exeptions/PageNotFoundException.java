package ru.moore.lesson10.exeptions;

public class PageNotFoundException extends RuntimeException {
    public PageNotFoundException(String message) {
        super(message);
    }
}
