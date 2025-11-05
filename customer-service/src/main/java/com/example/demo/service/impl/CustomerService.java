package com.example.demo.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.exception.CustomerNotFoundException;
import com.example.demo.model.Customer;
import com.example.demo.repository.CustomerRepository;
import com.example.demo.service.ICustomerService;

@Service
public class CustomerService implements ICustomerService {

    @Autowired
    CustomerRepository customerRepository;

    @Override
    public Customer createCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    @Override
    public List<Customer> readCustomer() {
        return customerRepository.findAll();
    }

    @Override
    public String deleteCustomer(String id) {
        int deleted = customerRepository.deleteByCustomerId(id);
        return "Customer with id: " + deleted + " deleted successfully.";
    }

    @Override
    public boolean isCustomerExist(String id) {
        return customerRepository.existsById(id);
    }

    @Override
    public Customer getCustomerById(String customerId) {
        Optional<Customer> opt = customerRepository.findById(customerId);
        if (opt.isPresent()) {
            return opt.get();
        } else {
            throw new CustomerNotFoundException();
        }
    }

}
