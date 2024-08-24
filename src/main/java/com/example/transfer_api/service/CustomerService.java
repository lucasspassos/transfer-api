package com.example.transfer_api.service;

import com.example.transfer_api.v1.model.Customer;

import java.util.List;

public interface CustomerService {

    void saveCustomer(Customer customer);

    List<Customer> getAllCustomers();

    Customer getCustomerByAccountNumber(Integer accountNumber);

}
