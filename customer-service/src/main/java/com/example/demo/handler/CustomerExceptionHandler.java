package com.example.demo.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.demo.exception.CustomerNotFoundException;
import com.example.demo.exception.DuplicateCustomerFoundException;
import com.example.demo.model.ResponseDefObject;
import com.example.demo.utility.Util;

@RestControllerAdvice
public class CustomerExceptionHandler {

    @ExceptionHandler(value = CustomerNotFoundException.class)
    public ResponseEntity<ResponseDefObject<String>> customerNotFoundException(CustomerNotFoundException exception) {
        return new ResponseEntity<ResponseDefObject<String>>(
                new ResponseDefObject<String>(HttpStatus.EXPECTATION_FAILED.value(), Util.ERROR,
                        exception.getMessage()),
                HttpStatus.EXPECTATION_FAILED);
    }

    @ExceptionHandler(value = DuplicateCustomerFoundException.class)
    public ResponseEntity<ResponseDefObject<String>> duplicateCustomerFoundException(
            DuplicateCustomerFoundException exception) {
        return new ResponseEntity<ResponseDefObject<String>>(
                new ResponseDefObject<String>(HttpStatus.CONFLICT.value(), Util.ERROR, exception.getMessage()),
                HttpStatus.CONFLICT);
    }
}

