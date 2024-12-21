package com.kyu.pappy.exceptions;

import org.springframework.http.HttpStatus;

public class ClinentErrorException extends RuntimeException {
    private final HttpStatus status;

    public ClinentErrorException(HttpStatus status, String message) {
        super(message);
        this.status = status;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
