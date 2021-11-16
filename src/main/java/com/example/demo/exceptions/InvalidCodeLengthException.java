package com.example.demo.exceptions;

public class InvalidCodeLengthException extends RuntimeException{
    public InvalidCodeLengthException(String message) {
        super(message);
    }
}