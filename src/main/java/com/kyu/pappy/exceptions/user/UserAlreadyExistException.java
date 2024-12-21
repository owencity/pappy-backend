package com.kyu.pappy.exceptions.user;

import com.kyu.pappy.exceptions.ClinentErrorException;
import org.springframework.http.HttpStatus;

public class UserAlreadyExistException extends ClinentErrorException {

    public UserAlreadyExistException() {
        super(HttpStatus.CONFLICT, "User Not Found");
    }

    public UserAlreadyExistException(String username) {
        super(HttpStatus.CONFLICT, "User with username" + "User already exists");
    }
}
