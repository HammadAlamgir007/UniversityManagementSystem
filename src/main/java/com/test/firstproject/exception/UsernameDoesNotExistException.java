package com.test.firstproject.exception;

public class UsernameDoesNotExistException extends RuntimeException {


    public UsernameDoesNotExistException(
            String username
    ) {

        super("Username Doesn't Exist " + username);

    }

}