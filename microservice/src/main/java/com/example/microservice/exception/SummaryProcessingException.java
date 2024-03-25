package com.example.microservice.exception;

public class SummaryProcessingException extends RuntimeException{
    public SummaryProcessingException(String message) {
        super(message);
    }
}
