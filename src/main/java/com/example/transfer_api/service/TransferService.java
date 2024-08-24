package com.example.transfer_api.service;

import com.example.transfer_api.v1.model.Transfer;
import com.example.transfer_api.v1.model.TransferRequest;

import java.util.List;

public interface TransferService {

    void makeTransfer(TransferRequest transfer);

    List<Transfer> getTransferHistory(Integer accountNumber);

}
