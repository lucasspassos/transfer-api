package com.example.transfer_api.service.impl;

import com.example.transfer_api.converter.CustomerConverter;
import com.example.transfer_api.entity.CustomerEntity;
import com.example.transfer_api.repository.CustomerRepository;
import com.example.transfer_api.service.CustomerService;
import com.example.transfer_api.v1.model.Customer;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService{
    private final CustomerRepository customerRepository;
    private final CustomerConverter customerConverter;

    @Override
    public void saveCustomer(final Customer customer) {

        try {
            customerRepository.save(customerConverter.toEntity(customer));
        } catch (DataIntegrityViolationException e) {
            throw new RuntimeException("Account number already exists: " + customer.getAccountNumber());
        }
    }

    @Override
    public List<Customer> getAllCustomers() {
        return customerConverter.toResponse(customerRepository.findAll());
    }

    @Override
    public Customer getCustomerByAccountNumber(Integer accountNumber) {
        return  customerConverter.toClass(customerRepository.findByAccountNumber(accountNumber));
    }
}
