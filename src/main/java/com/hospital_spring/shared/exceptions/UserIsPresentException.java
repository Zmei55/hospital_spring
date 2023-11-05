package com.hospital_spring.shared.exceptions;

public class UserIsPresentException extends RuntimeException {
    public UserIsPresentException(String message) {super(message);}
}
