package com.mvc_thymeleaf.services.exceptions;

public class LoginExisteException extends RuntimeException {

    public LoginExisteException(String message) {
        super(message);
    }
}
