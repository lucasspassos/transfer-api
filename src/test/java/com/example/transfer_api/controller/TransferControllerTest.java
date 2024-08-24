package com.example.transfer_api.controller;

import com.example.transfer_api.service.TransferService;
import com.example.transfer_api.v1.model.Transfer;
import com.example.transfer_api.v1.model.TransferRequest;
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

public class TransferControllerTest {

    @Mock
    private TransferService transferService;

    @InjectMocks
    private TransferController transferController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetTransferHistory_Success() {
        Integer accountNumber = 12345;
        List<Transfer> transfers = new ArrayList<>();
        transfers.add(new Transfer());
        when(transferService.getTransferHistory(accountNumber)).thenReturn(transfers);

        ResponseEntity<List<Transfer>> response = transferController.getTransferHistory(accountNumber);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(transfers, response.getBody());
    }

    @Test
    public void testGetTransferHistory_NotFound() {
        Integer accountNumber = 12345;
        when(transferService.getTransferHistory(accountNumber)).thenReturn(new ArrayList<>());

        ResponseEntity<List<Transfer>> response = transferController.getTransferHistory(accountNumber);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testMakeTransfer_Success() {
        TransferRequest transferRequest = new TransferRequest();
        doNothing().when(transferService).makeTransfer(transferRequest);

        ResponseEntity<Void> response = transferController.makeTransfer(transferRequest);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    public void testMakeTransfer_InternalServerError() {
        TransferRequest transferRequest = new TransferRequest();
        doThrow(new RuntimeException()).when(transferService).makeTransfer(transferRequest);

        ResponseEntity<Void> response = transferController.makeTransfer(transferRequest);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }
}
