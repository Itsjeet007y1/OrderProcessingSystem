package com.example.demo.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.example.demo.builder.CustomerResponseBuilder;
import com.example.demo.exception.DuplicateCustomerFoundException;
import com.example.demo.exception.CustomerNotFoundException;
import com.example.demo.model.ResponseDefObject;
import com.example.demo.model.Customer;
import com.example.demo.service.ICustomerService;
import com.example.demo.utility.Util;
import com.example.demo.vo.CustomerResponse;

@RestController
public class CustomerController {

    private static final Logger logger = LoggerFactory.getLogger(CustomerController.class);

    @Autowired
    ICustomerService customerService;

    @Autowired
    RestTemplate restTemplate;

    @PostMapping("/savecustomer")
    public ResponseEntity<ResponseDefObject<Customer>> createCustomer(@RequestBody Customer customer) {
        logger.info("savecustomer end point is called.");
        if (!customerService.isCustomerExist(customer.getCustomerId())) {
            Customer savedCustomer = customerService.createCustomer(customer);
            return new ResponseEntity<ResponseDefObject<Customer>>(
                    new ResponseDefObject<Customer>(HttpStatus.CREATED.value(), Util.SUCCESS, savedCustomer),
                    HttpStatus.ACCEPTED);
        } else {
            logger.warn("Customer already exist with the given customerId.");
            throw new DuplicateCustomerFoundException();
        }
    }

    @PostMapping("/updatecustomer")
    public ResponseEntity<ResponseDefObject<Customer>> updateCustomer(@RequestBody Customer customer) {
        logger.info("updatecustomer end point is called.");
        if (customerService.isCustomerExist(customer.getCustomerId())) {
            return new ResponseEntity<ResponseDefObject<Customer>>(
                    new ResponseDefObject<Customer>(HttpStatus.CREATED.value(), Util.SUCCESS, customerService.createCustomer(customer)),
                    HttpStatus.ACCEPTED);
        } else {
            logger.warn("Customer with given id does not exist.");
            throw new CustomerNotFoundException();
        }
    }

    @GetMapping("/getcustomers")
    public ResponseEntity<ResponseDefObject<List<Customer>>> getCustomers() {
        logger.info("getcustomers end point is called.");
        return new ResponseEntity<ResponseDefObject<List<Customer>>>(
                new ResponseDefObject<List<Customer>>(HttpStatus.CREATED.value(), Util.SUCCESS, customerService.readCustomer()),
                HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/deletecustomer/{id}")
    public ResponseEntity<ResponseDefObject<String>> deleteCustomer(@PathVariable("id") String id) {
        logger.info("deletecustomer end point is called.");
        if (customerService.isCustomerExist(id)) {
            return new ResponseEntity<ResponseDefObject<String>>(
                    new ResponseDefObject<String>(HttpStatus.CREATED.value(), Util.SUCCESS, customerService.deleteCustomer(id)), HttpStatus.ACCEPTED);
        } else {
            logger.warn("Customer does not exist with the given customerId.");
            throw new CustomerNotFoundException();
        }
    }

    @GetMapping("/getcustomer/{customerId}")
    public ResponseEntity<ResponseDefObject<CustomerResponse>> getCustomerById(@PathVariable("customerId") String customerId) {
        logger.info("getcustomer end point is called.");
        if (customerService.isCustomerExist(customerId)) {
            CustomerResponse customerResponse = CustomerResponseBuilder.getCustomerResponse(customerService.getCustomerById(customerId));
            return new ResponseEntity<ResponseDefObject<CustomerResponse>>(
                    new ResponseDefObject<CustomerResponse>(HttpStatus.CREATED.value(), Util.SUCCESS, customerResponse),
                    HttpStatus.ACCEPTED);
        } else {
            logger.warn("Customer already exist with the given customerId.");
            throw new DuplicateCustomerFoundException();
        }
    }

}

