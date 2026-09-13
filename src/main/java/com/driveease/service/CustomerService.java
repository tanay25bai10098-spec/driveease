package com.driveease.service;

import com.driveease.exception.ApplicationException;
import com.driveease.model.Customer;
import com.driveease.repository.CustomerRepository;

import java.util.List;

public class CustomerService {
    private final CustomerRepository repository;

    public CustomerService(CustomerRepository repository) {
        this.repository = repository;
    }

    public void registerCustomer(Customer customer) {
        if (customer.getName().isBlank()
                || customer.getPhone().isBlank()
                || customer.getEmail().isBlank()) {
            throw new ApplicationException("All customer fields are required.");
        }

        repository.save(customer);
    }

    public List<Customer> getAllCustomers() {
        return repository.findAll();
    }

    public Customer getCustomer(int id) {
        return repository.findById(id)
                .orElseThrow(() -> new ApplicationException(
                        "Customer not found."));
    }
}
