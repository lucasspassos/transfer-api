package com.example.transfer_api.integration;

import com.example.transfer_api.entity.CustomerEntity;
import com.example.transfer_api.repository.CustomerRepository;
import com.example.transfer_api.repository.TransferRepository;
import com.example.transfer_api.service.TransferService;
import com.example.transfer_api.v1.model.TransferRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class TransferServiceIntegrationTest {

    @Autowired
    private TransferService transferService;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private TransferRepository transferRepository;

    @BeforeEach
    public void setup() {
        transferRepository.deleteAll();
        customerRepository.deleteAll();

        CustomerEntity customer1 = new CustomerEntity();
        customer1.setAccountNumber(123);
        customer1.setBalance(1000.0);
        customerRepository.save(customer1);

        CustomerEntity customer2 = new CustomerEntity();
        customer2.setAccountNumber(456);
        customer2.setBalance(500.0);
        customerRepository.save(customer2);
    }

    @Test
    @Transactional
    public void testMakeTransferSuccess() {
        TransferRequest transferRequest = new TransferRequest();
        transferRequest.setSourceAccount(123);
        transferRequest.setDestinationAccount(456);
        transferRequest.setAmount(200.0f);

        transferService.makeTransfer(transferRequest);

        CustomerEntity customer1 = customerRepository.findByAccountNumber(123);
        CustomerEntity customer2 = customerRepository.findByAccountNumber(456);

        assertEquals(800.0f, customer1.getBalance());
        assertEquals(700.0f, customer2.getBalance());
    }

    @Test
    @Transactional
    public void testMakeTransferInsufficientFunds() {
        TransferRequest transferRequest = new TransferRequest();
        transferRequest.setSourceAccount(123);
        transferRequest.setDestinationAccount(456);
        transferRequest.setAmount(1200.0f);

        assertThrows(RuntimeException.class, () -> {
            transferService.makeTransfer(transferRequest);
        });

        CustomerEntity customer1 = customerRepository.findByAccountNumber(123);
        CustomerEntity customer2 = customerRepository.findByAccountNumber(456);

        assertEquals(1000.0f, customer1.getBalance());
        assertEquals(500.0f, customer2.getBalance());
    }
}
