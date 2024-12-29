package com.kyu.pappy.config.exceptions.user;

import com.kyu.pappy.config.exceptions.ClientErrorException;
import org.springframework.http.HttpStatus;

public class UserNotAllowException extends ClientErrorException {


    public UserNotAllowException() {
        super(HttpStatus.FORBIDDEN, "User not allowed");
    }

}
