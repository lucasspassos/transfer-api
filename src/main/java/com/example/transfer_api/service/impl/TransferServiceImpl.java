package com.example.transfer_api.service.impl;

import com.example.transfer_api.converter.TransferConverter;
import com.example.transfer_api.entity.CustomerEntity;
import com.example.transfer_api.repository.TransferRepository;
import com.example.transfer_api.service.CustomerService;
import com.example.transfer_api.service.TransferRulesService;
import com.example.transfer_api.service.TransferService;
import com.example.transfer_api.v1.model.Customer;
import com.example.transfer_api.v1.model.Transfer;
import com.example.transfer_api.v1.model.TransferRequest;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TransferServiceImpl implements TransferService {
    private final TransferRepository transferRepository;
    private final TransferConverter transferConverter;

    private final CustomerService customerService;

    private final TransferRulesService transferRulesService;

    @Override
    public void makeTransfer(final TransferRequest transferRequest) {
        try {

            var sourceAccount = customerService.getCustomerByAccountNumber(transferRequest.getSourceAccount());
            var destinationAccount = customerService.getCustomerByAccountNumber(transferRequest.getDestinationAccount());

            if(isTransactionAuthorized(sourceAccount, destinationAccount, transferRequest)){
                sourceAccount.setBalance(sourceAccount.getBalance() - transferRequest.getAmount());
                destinationAccount.setBalance(destinationAccount.getBalance() + transferRequest.getAmount());

                saveBalance(sourceAccount);
                saveBalance(destinationAccount);
                registerTransferHistory(transferRequest, "SUCCESS");
            }else{
                registerTransferHistory(transferRequest, "ERROR");
                throw new RuntimeException("Transaction not authorized");
            }

        } catch (Exception e) {
            registerTransferHistory(transferRequest, "ERROR");
            throw new RuntimeException("Account number already exists: ");
        }
    }

    private Boolean isTransactionAuthorized(Customer sourceAccount, Customer destinationAccount, TransferRequest transferRequest) {
        if (ObjectUtils.isNotEmpty(sourceAccount) &&
            ObjectUtils.isNotEmpty(destinationAccount) &&
            ObjectUtils.isNotEmpty(transferRequest)) {
                return transferRulesService.isAuthorizedToTransfer(sourceAccount, transferRequest.getAmount());
        }
        return false;
    }

    @Override
    public List<Transfer> getTransferHistory(Integer accountNumber) {
        return transferConverter.toResponse(
                transferRepository.findBySourceAccountOrDestinationAccountOrderByTimestampAsc(accountNumber));
    }

    private void saveBalance(Customer customer){
        customerService.saveCustomer(customer);
    }

    private void registerTransferHistory(TransferRequest transferRequest, String status){
        transferRepository.save(transferConverter.toEntity(transferRequest, status));
    }

}
