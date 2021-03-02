package com.camunda.camundademo.exceptions;

public class DirectoryCreationException extends RuntimeException {
    public DirectoryCreationException(String errorMessage, Throwable err) {
        super(errorMessage, err);
    }
}
