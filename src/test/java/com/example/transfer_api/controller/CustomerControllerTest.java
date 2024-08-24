package com.example.transfer_api.controller;

import com.example.transfer_api.service.CustomerService;
import com.example.transfer_api.v1.model.Customer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class CustomerControllerTest {

    @Mock
    private CustomerService customerService;

    @InjectMocks
    private CustomerController customerController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testListCustomers_Success() {
        List<Customer> customers = new ArrayList<>();
        customers.add(new Customer());
        when(customerService.getAllCustomers()).thenReturn(customers);

        ResponseEntity<List<Customer>> response = customerController.listCustomers();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(customers, response.getBody());
    }

    @Test
    public void testListCustomers_NotFound() {
        when(customerService.getAllCustomers()).thenReturn(new ArrayList<>());

        ResponseEntity<List<Customer>> response = customerController.listCustomers();

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testGetCustomerByAccountNumber_Success() {
        Integer accountNumber = 12345;
        Customer customer = new Customer();
        when(customerService.getCustomerByAccountNumber(accountNumber)).thenReturn(customer);

        ResponseEntity<Customer> response = customerController.getCustomerByAccountNumber(accountNumber);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(customer, response.getBody());
    }

    @Test
    public void testGetCustomerByAccountNumber_NotFound() {
        Integer accountNumber = 12345;
        when(customerService.getCustomerByAccountNumber(accountNumber)).thenReturn(null);

        ResponseEntity<Customer> response = customerController.getCustomerByAccountNumber(accountNumber);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testRegisterCustomer_Success() {
        Customer customer = new Customer();
        doNothing().when(customerService).saveCustomer(customer);

        ResponseEntity<Void> response = customerController.registerCustomer(customer);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    public void testRegisterCustomer_InternalServerError() {
        Customer customer = new Customer();
        doThrow(new RuntimeException()).when(customerService).saveCustomer(customer);

        ResponseEntity<Void> response = customerController.registerCustomer(customer);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }
}