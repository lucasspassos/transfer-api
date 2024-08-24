package com.example.transfer_api.converter;

import com.example.transfer_api.entity.TransferEntity;
import com.example.transfer_api.util.Util;
import com.example.transfer_api.v1.model.Transfer;
import com.example.transfer_api.v1.model.TransferRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class TransferConverterTest {

    @InjectMocks
    private TransferConverter transferConverter;

    @Mock
    private Util util;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testToEntity() {
        TransferRequest transferRequest = new TransferRequest();
        transferRequest.setSourceAccount(123);
        transferRequest.setDestinationAccount(456);
        transferRequest.setAmount(100.0F);

        String status = "COMPLETED";
        LocalDateTime now = LocalDateTime.now();

        TransferEntity transferEntity = transferConverter.toEntity(transferRequest, status);

        assertEquals(transferRequest.getSourceAccount(), transferEntity.getSourceAccount());
        assertEquals(transferRequest.getDestinationAccount(), transferEntity.getDestinationAccount());
        assertEquals(transferRequest.getAmount(), transferEntity.getAmount());
        assertEquals(status, transferEntity.getStatus());
        assertEquals(now.getYear(), transferEntity.getTimestamp().getYear());
        assertEquals(now.getMonth(), transferEntity.getTimestamp().getMonth());
        assertEquals(now.getDayOfMonth(), transferEntity.getTimestamp().getDayOfMonth());
        assertEquals(now.getHour(), transferEntity.getTimestamp().getHour());
        assertEquals(now.getMinute(), transferEntity.getTimestamp().getMinute());
    }

    @Test
    void testToResponse() {
        TransferEntity transferEntity = new TransferEntity();
        List<TransferEntity> entities = new ArrayList<>();
        entities.add(transferEntity);

        Transfer transfer = new Transfer();
        when(util.parseToType(entities, Transfer[].class)).thenReturn(new Transfer[]{transfer});

        List<Transfer> result = transferConverter.toResponse(entities);

        assertEquals(1, result.size());
        assertEquals(transfer, result.get(0));
    }

    @Test
    void testToResponseEmptyList() {
        List<TransferEntity> entities = new ArrayList<>();

        List<Transfer> result = transferConverter.toResponse(entities);

        assertEquals(0, result.size());
    }

    @Test
    void testToResponseException() {
        List<TransferEntity> entities = new ArrayList<>();
        when(util.parseToType(entities, Transfer[].class)).thenThrow(new RuntimeException("Error"));

        List<Transfer> result = transferConverter.toResponse(entities);

        assertEquals(0, result.size());
    }
}
