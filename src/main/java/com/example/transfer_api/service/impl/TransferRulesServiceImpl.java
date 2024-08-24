package com.example.transfer_api.service.impl;

import com.example.transfer_api.entity.CustomerEntity;
import com.example.transfer_api.service.TransferRulesService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TransferRulesServiceImpl implements TransferRulesService {

    private final Integer maxLimit = 10000;

    @Override
    public Boolean isAuthorizedToTransfer(CustomerEntity customer, Float amount) {

        if (ObjectUtils.isNotEmpty(customer) && amount != null && amount > 0) {
            return customer.getBalance() >= amount && amount <= maxLimit;
        }
        return false;
    }

}
