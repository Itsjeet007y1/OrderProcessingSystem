package com.example.demo.service;

import java.util.List;

import com.example.demo.model.Customer;

public interface ICustomerService {

    public Customer createCustomer(Customer customer);

    public List<Customer> readCustomer();

    public String deleteCustomer(String id);

    public boolean isCustomerExist(String id);

    public Customer getCustomerById(String customerId);
}

