package org.example.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class UserProcessingException extends RuntimeException {

    public UserProcessingException(String message) {
        super(message);
    }
}
