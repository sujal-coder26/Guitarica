package com.web.guitarapp.exception;

public class ApiException extends RuntimeException{

    public ApiException(String message){
        super(message);
    }
}
