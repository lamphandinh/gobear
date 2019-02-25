package com.example.domain.communication.error;

public class UserNonRegisterException extends RuntimeException {
    private String message;
    public UserNonRegisterException(String s) {
        super(s);
        message = s;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
