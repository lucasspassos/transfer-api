package com.example.transfer_api.service.impl;

import com.example.transfer_api.converter.CustomerConverter;
import com.example.transfer_api.entity.CustomerEntity;
import com.example.transfer_api.repository.CustomerRepository;
import com.example.transfer_api.v1.model.Customer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class CustomerServiceImplTest {

    @InjectMocks
    private CustomerServiceImpl customerService;

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private CustomerConverter customerConverter;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveCustomer() {
        Customer customer = new Customer();
        CustomerEntity customerEntity = new CustomerEntity();

        when(customerConverter.toEntity(customer)).thenReturn(customerEntity);

        customerService.saveCustomer(customer);

        verify(customerRepository, times(1)).save(customerEntity);
    }

    @Test
    void testSaveCustomerThrowsException() {
        Customer customer = new Customer();
        when(customerConverter.toEntity(customer)).thenThrow(new DataIntegrityViolationException(""));

        RuntimeException thrown = assertThrows(RuntimeException.class, () -> {
            customerService.saveCustomer(customer);
        });

        assertEquals("Account number already exists: null", thrown.getMessage());
    }

    @Test
    void testGetAllCustomers() {
        CustomerEntity customerEntity = new CustomerEntity();
        List<CustomerEntity> customerEntities = new ArrayList<>();
        customerEntities.add(customerEntity);

        Customer customer = new Customer();
        when(customerRepository.findAll()).thenReturn(customerEntities);
        when(customerConverter.toResponse(customerEntities)).thenReturn(List.of(customer));

        List<Customer> result = customerService.getAllCustomers();

        assertEquals(1, result.size());
        assertEquals(customer, result.get(0));
    }

    @Test
    void testGetCustomerByAccountNumber() {
        Integer accountNumber = 123;
        CustomerEntity customerEntity = new CustomerEntity();
        Customer customer = new Customer();

        when(customerRepository.findByAccountNumber(accountNumber)).thenReturn(customerEntity);
        when(customerConverter.toClass(customerEntity)).thenReturn(customer);

        Customer result = customerService.getCustomerByAccountNumber(accountNumber);

        assertEquals(customer, result);
    }
}
