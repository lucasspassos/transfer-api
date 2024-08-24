package com.example.transfer_api.service;

import com.example.transfer_api.entity.CustomerEntity;

public interface TransferRulesService {

    Boolean isAuthorizedToTransfer(CustomerEntity customerSource, Float amount);

}
