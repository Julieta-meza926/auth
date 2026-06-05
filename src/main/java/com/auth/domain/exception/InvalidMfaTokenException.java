package com.auth.domain.exception;

public class InvalidMfaTokenException
        extends RuntimeException {

    public InvalidMfaTokenException(
            String message
    ) {
        super(message);
    }
}