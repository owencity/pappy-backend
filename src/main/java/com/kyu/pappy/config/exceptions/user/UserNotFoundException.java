package com.kyu.pappy.config.exceptions.user;

import com.kyu.pappy.config.exceptions.ClientErrorException;
import org.springframework.http.HttpStatus;

public class UserNotFoundException extends ClientErrorException {

    public UserNotFoundException() {
        super(HttpStatus.NOT_FOUND, "user Not Found");
    }

    public UserNotFoundException(String userEmail) {
        super(HttpStatus.NOT_FOUND, "User with email" + userEmail + " not found");
    }
}
