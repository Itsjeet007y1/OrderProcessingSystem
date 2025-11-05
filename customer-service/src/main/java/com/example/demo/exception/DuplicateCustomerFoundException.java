package com.example.demo.exception;

public class DuplicateCustomerFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public DuplicateCustomerFoundException() {
        super("Duplicate customer found Exception occured.");
    }
}

