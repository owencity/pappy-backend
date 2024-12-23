package com.kyu.pappy.config.exceptions.user;

import com.kyu.pappy.config.exceptions.ClientErrorException;
import org.springframework.http.HttpStatus;

public class UserAlreadyExistException extends ClientErrorException {

    public UserAlreadyExistException() {
        super(HttpStatus.CONFLICT, "User Not Found");
    }

    public UserAlreadyExistException(String username) {
        super(HttpStatus.CONFLICT, "User with username" + "User already exists");
    }
}
