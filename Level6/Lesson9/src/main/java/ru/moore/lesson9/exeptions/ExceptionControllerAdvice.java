package ru.moore.lesson9.exeptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionControllerAdvice {

    @ExceptionHandler
    public ResponseEntity<MarketError> handleResourceNotFoundException(ResourceNotFoundException e) {
        MarketError err = new MarketError(HttpStatus.NOT_FOUND.value(), e.getMessage());
        return new ResponseEntity<>(err, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<MarketError> handlePageNotFoundException(PageNotFoundException e) {
        MarketError err = new MarketError(HttpStatus.NOT_FOUND.value(), e.getMessage());
        return new ResponseEntity<>(err, HttpStatus.NOT_FOUND);
    }
}
