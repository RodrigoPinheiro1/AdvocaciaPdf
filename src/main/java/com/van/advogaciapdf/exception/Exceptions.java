package com.van.advogaciapdf.exception;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class Exceptions {


    @ExceptionHandler(Exception.class)
    public String exception(Exception e) {


        return e.getMessage();
    }
}
