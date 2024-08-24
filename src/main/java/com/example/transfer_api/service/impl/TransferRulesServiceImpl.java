package com.example.transfer_api.service.impl;

import com.example.transfer_api.converter.TransferConverter;
import com.example.transfer_api.repository.TransferRepository;
import com.example.transfer_api.service.CustomerService;
import com.example.transfer_api.service.TransferRulesService;
import com.example.transfer_api.service.TransferService;
import com.example.transfer_api.v1.model.Customer;
import com.example.transfer_api.v1.model.Transfer;
import com.example.transfer_api.v1.model.TransferRequest;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TransferRulesServiceImpl implements TransferRulesService {

    private final Integer maxLimit = 10000;

    @Override
    public Boolean isAuthorizedToTransfer(Customer customer, Float amount) {

        if (ObjectUtils.isNotEmpty(customer) && amount != null && amount > 0) {
            return customer.getBalance() >= amount && amount <= maxLimit;
        }
        return false;
    }


}
