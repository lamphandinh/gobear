package com.example.domain.communication.error;

public class WrongPassException extends RuntimeException {
    public WrongPassException(String s) {
        super(s);
    }
}
