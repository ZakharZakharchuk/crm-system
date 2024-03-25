package org.example.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class TraineeProcessingException extends RuntimeException {

    public TraineeProcessingException(String message) {
        super(message);
    }
}
