package com.example.transfer_api.converter;

import com.example.transfer_api.entity.TransferEntity;
import com.example.transfer_api.util.Util;
import com.example.transfer_api.v1.model.Transfer;
import com.example.transfer_api.v1.model.TransferRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
public class TransferConverter {

    public final Util util;

    public TransferEntity toEntity(final TransferRequest transferRequest, String status){
        return TransferEntity.builder()
                .sourceAccount(transferRequest.getSourceAccount())
                .destinationAccount(transferRequest.getDestinationAccount())
                .amount(transferRequest.getAmount())
                .timestamp(LocalDateTime.now())
                .status(status)
                .build();
    }

    public List<Transfer> toResponse(
            final List<TransferEntity> entities) {

        List<Transfer> transfers = new ArrayList<>();

        try {
            if (entities != null && !entities.isEmpty()) {
                Transfer[] transferArray = util.parseToType(entities, Transfer[].class);
                if (transferArray != null) {
                    transfers = Arrays.asList(transferArray);
                }
            }

        } catch (Exception e) {

        }

        return transfers;
    }
}
