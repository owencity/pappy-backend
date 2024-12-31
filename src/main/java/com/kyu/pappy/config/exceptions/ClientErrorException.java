package com.kyu.pappy.config.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ClientErrorException extends RuntimeException {
    private final HttpStatus status;

    public ClientErrorException(HttpStatus status, String message) {
        super(message);
        this.status = status;
    }

}
