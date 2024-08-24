package com.example.transfer_api.integration;

import com.example.transfer_api.repository.CustomerRepository;
import com.example.transfer_api.service.CustomerService;
import com.example.transfer_api.v1.model.Customer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class CustomerServiceIntegrationTest {

    @Autowired
    private CustomerService customerService;
    @Autowired
    private CustomerRepository customerRepository;
    @BeforeEach
    public void setup() {
        customerRepository.deleteAll();
    }

    @Test
    @Transactional
    public void testSaveCustomer() {
        Customer customer = new Customer();
        customer.setAccountNumber(789);
        customer.setBalance(1500.0f);

        customerService.saveCustomer(customer);

        Customer savedCustomer = customerService.getCustomerByAccountNumber(789);
        assertNotNull(savedCustomer);
        assertEquals(1500.0f, savedCustomer.getBalance());
    }

    @Test
    @Transactional
    public void testSaveCustomerDuplicateAccount() {
        Customer customer = new Customer();
        customer.setAccountNumber(789);
        customer.setBalance(1500.0f);
        customerService.saveCustomer(customer);

        Customer duplicateCustomer = new Customer();
        duplicateCustomer.setAccountNumber(789);
        duplicateCustomer.setBalance(2000.0f);

        assertThrows(RuntimeException.class, () -> {
            customerService.saveCustomer(duplicateCustomer);
        });
    }
}
