package com.example.transfer_api.service.impl;

import com.example.transfer_api.converter.TransferConverter;
import com.example.transfer_api.entity.CustomerEntity;
import com.example.transfer_api.repository.CustomerRepository;
import com.example.transfer_api.repository.TransferRepository;
import com.example.transfer_api.service.TransferRulesService;
import com.example.transfer_api.v1.model.Transfer;
import com.example.transfer_api.v1.model.TransferRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class TransferServiceImplTest {

    @Mock
    private TransferRepository transferRepository;
    @Mock
    private CustomerRepository customerRepository;
    @Mock
    private TransferConverter transferConverter;
    @Mock
    private TransferRulesService transferRulesService;
    @InjectMocks
    private TransferServiceImpl transferServiceImpl;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testMakeTransfer_Success() {
        TransferRequest transferRequest = new TransferRequest();
        transferRequest.setSourceAccount(123);
        transferRequest.setDestinationAccount(456);
        transferRequest.setAmount(100.0F);

        CustomerEntity sourceCustomer = new CustomerEntity();
        sourceCustomer.setAccountNumber(123);
        sourceCustomer.setBalance(200.0);

        CustomerEntity destinationCustomer = new CustomerEntity();
        destinationCustomer.setAccountNumber(456);
        destinationCustomer.setBalance(100.0);

        when(customerRepository.findByAccountNumberForUpdate(123)).thenReturn(Optional.of(sourceCustomer));
        when(customerRepository.findByAccountNumberForUpdate(456)).thenReturn(Optional.of(destinationCustomer));
        when(transferRulesService.isAuthorizedToTransfer(sourceCustomer, 100.0F)).thenReturn(true);

        transferServiceImpl.makeTransfer(transferRequest);

        verify(customerRepository).save(sourceCustomer);
        verify(customerRepository).save(destinationCustomer);
        verify(transferRepository).save(any());
    }

    @Test
    public void testMakeTransfer_TransactionNotAuthorized() {
        TransferRequest transferRequest = new TransferRequest();
        transferRequest.setSourceAccount(123);
        transferRequest.setDestinationAccount(456);
        transferRequest.setAmount(100.0F);

        CustomerEntity sourceCustomer = new CustomerEntity();
        sourceCustomer.setAccountNumber(123);
        sourceCustomer.setBalance(200.0);

        CustomerEntity destinationCustomer = new CustomerEntity();
        destinationCustomer.setAccountNumber(456);
        destinationCustomer.setBalance(100.0);

        when(customerRepository.findByAccountNumberForUpdate(123)).thenReturn(Optional.of(sourceCustomer));
        when(customerRepository.findByAccountNumberForUpdate(456)).thenReturn(Optional.of(destinationCustomer));
        when(transferRulesService.isAuthorizedToTransfer(sourceCustomer, 100.0F)).thenReturn(false);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            transferServiceImpl.makeTransfer(transferRequest);
        });

        assertEquals("Erro ao processar a transferencia", exception.getMessage());

        verify(customerRepository, never()).save(any());
        verify(transferRepository, times(2)).save(any());
    }

    @Test
    public void testMakeTransfer_SourceAccountNotFound() {
        TransferRequest transferRequest = new TransferRequest();
        transferRequest.setSourceAccount(123);
        transferRequest.setDestinationAccount(456);
        transferRequest.setAmount(100.0F);

        when(customerRepository.findByAccountNumberForUpdate(123)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            transferServiceImpl.makeTransfer(transferRequest);
        });

        assertEquals("Erro ao processar a transferencia", exception.getMessage());

        verify(customerRepository, never()).save(any());
        verify(transferRepository).save(any());
    }

    @Test
    public void testMakeTransfer_DestinationAccountNotFound() {
        TransferRequest transferRequest = new TransferRequest();
        transferRequest.setSourceAccount(123);
        transferRequest.setDestinationAccount(456);
        transferRequest.setAmount(100.0F);

        CustomerEntity sourceCustomer = new CustomerEntity();
        sourceCustomer.setAccountNumber(123);
        sourceCustomer.setBalance(200.0);

        when(customerRepository.findByAccountNumberForUpdate(123)).thenReturn(Optional.of(sourceCustomer));
        when(customerRepository.findByAccountNumberForUpdate(456)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            transferServiceImpl.makeTransfer(transferRequest);
        });

        assertEquals("Erro ao processar a transferencia", exception.getMessage());

        verify(customerRepository, never()).save(any());
        verify(transferRepository).save(any());
    }

    @Test
    public void testGetTransferHistory() {
        Integer accountNumber = 123;
        List<Transfer> transfers = new ArrayList<>();
        when(transferRepository.findBySourceAccountOrDestinationAccountOrderByTimestampAsc(accountNumber, accountNumber)).thenReturn(new ArrayList<>());
        when(transferConverter.toResponse(any())).thenReturn(transfers);

        List<Transfer> result = transferServiceImpl.getTransferHistory(accountNumber);

        assertEquals(transfers, result);
    }
}
