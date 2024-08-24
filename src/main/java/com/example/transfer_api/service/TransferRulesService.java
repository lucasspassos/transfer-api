package com.example.transfer_api.service;

import com.example.transfer_api.v1.model.Customer;
import com.example.transfer_api.v1.model.Transfer;
import com.example.transfer_api.v1.model.TransferRequest;

import java.util.List;

public interface TransferRulesService {

    Boolean isAuthorizedToTransfer(Customer customerSource, Float amount);

}
