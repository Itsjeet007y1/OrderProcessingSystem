package com.example.demo.builder;

import com.example.demo.model.Customer;
import com.example.demo.vo.CustomerResponse;

public class CustomerResponseBuilder {

    public static CustomerResponse getCustomerResponse(Customer customer) {
        CustomerResponse resp = new CustomerResponse();
        resp.setCustomerId(customer.getCustomerId());
        resp.setFirstName(customer.getFirstName());
        resp.setSirName(customer.getSirName());
        resp.setDob(customer.getDob());
        resp.setTitle(customer.getTitle());
        return resp;
    }
}

