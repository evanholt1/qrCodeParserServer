package com.example.demo.exceptions;

public class InvalidJsonFormatException extends RuntimeException{
    public InvalidJsonFormatException(String message) {
        super(message);
    }
}