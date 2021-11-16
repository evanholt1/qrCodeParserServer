package com.example.demo.exceptions;

public class InvalidQrCodeException extends RuntimeException{
    public InvalidQrCodeException(String message) {
        super(message);
    }
}