package com.example.demo.exceptions;

public class UnsuccessfulStandardReadException extends RuntimeException{
    public UnsuccessfulStandardReadException(String message) {
        super(message);
    }
}