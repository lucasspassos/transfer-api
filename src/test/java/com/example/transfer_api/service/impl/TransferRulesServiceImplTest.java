package com.example.transfer_api.service.impl;

import com.example.transfer_api.entity.CustomerEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TransferRulesServiceImplTest {

    @InjectMocks
    private TransferRulesServiceImpl transferRulesServiceImpl;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testIsAuthorizedToTransfer_Success() {
        CustomerEntity customer = new CustomerEntity();
        customer.setBalance(5000.0);

        Boolean result = transferRulesServiceImpl.isAuthorizedToTransfer(customer, 1000.0F);

        assertTrue(result);
    }

    @Test
    public void testIsAuthorizedToTransfer_AmountExceedsBalance() {
        CustomerEntity customer = new CustomerEntity();
        customer.setBalance(500.0);

        Boolean result = transferRulesServiceImpl.isAuthorizedToTransfer(customer, 1000.0F);

        assertFalse(result);
    }

    @Test
    public void testIsAuthorizedToTransfer_AmountExceedsMaxLimit() {
        CustomerEntity customer = new CustomerEntity();
        customer.setBalance(15000.0);

        Boolean result = transferRulesServiceImpl.isAuthorizedToTransfer(customer, 15000.0F);

        assertFalse(result);
    }

    @Test
    public void testIsAuthorizedToTransfer_CustomerIsEmpty() {
        CustomerEntity customer = null;

        Boolean result = transferRulesServiceImpl.isAuthorizedToTransfer(customer, 1000.0F);

        assertFalse(result);
    }

    @Test
    public void testIsAuthorizedToTransfer_AmountIsNull() {
        CustomerEntity customer = new CustomerEntity();
        customer.setBalance(5000.0);

        Boolean result = transferRulesServiceImpl.isAuthorizedToTransfer(customer, null);

        assertFalse(result);
    }

    @Test
    public void testIsAuthorizedToTransfer_AmountIsNegative() {
        CustomerEntity customer = new CustomerEntity();
        customer.setBalance(5000.0);

        Boolean result = transferRulesServiceImpl.isAuthorizedToTransfer(customer, -1000.0F);

        assertFalse(result);
    }
}
