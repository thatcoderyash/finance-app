package com.zorvyn.finance.exceptions;

import org.springframework.http.HttpStatus;

public class UserNotFoundException extends ApiException {

    public UserNotFoundException(Long id) {
        super("User not found with id: " + id, HttpStatus.NOT_FOUND);
    }

    public UserNotFoundException(String email) {
        super("User not found with email: " + email, HttpStatus.NOT_FOUND);
    }
}
