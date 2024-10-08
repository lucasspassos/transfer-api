package com.example.transfer_api.controller;

import com.example.transfer_api.service.TransferService;
import com.example.transfer_api.v1.api.TransfersApi;
import com.example.transfer_api.v1.model.Transfer;
import com.example.transfer_api.v1.model.TransferRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1")
public class TransferController implements TransfersApi {

    private final TransferService transferService;
    @Override
    public ResponseEntity<List<Transfer>> getTransferHistory(Integer accountNumber) {
        List<Transfer> transfers = transferService.getTransferHistory(accountNumber);

        if (transfers == null || transfers.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(transfers, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> makeTransfer(TransferRequest transfer) {
        try{
            transferService.makeTransfer(transfer);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
