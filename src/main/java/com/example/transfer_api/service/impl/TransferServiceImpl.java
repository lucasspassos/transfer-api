package com.example.transfer_api.service.impl;

import com.example.transfer_api.v1.model.Transfer;import com.example.transfer_api.converter.TransferConverter;
import com.example.transfer_api.entity.CustomerEntity;
import com.example.transfer_api.repository.CustomerRepository;
import com.example.transfer_api.repository.TransferRepository;
import com.example.transfer_api.service.TransferRulesService;
import com.example.transfer_api.service.TransferService;
import com.example.transfer_api.v1.model.TransferRequest;
import com.example.transfer_api.v1.model.Transfer;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TransferServiceImpl implements TransferService {
    private final TransferRepository transferRepository;
    private final CustomerRepository customerRepository;
    private final TransferConverter transferConverter;
    private final TransferRulesService transferRulesService;

    @Override
    @Transactional
    public void makeTransfer(final TransferRequest transferRequest) {
        try {
            var sourceAccount = customerRepository.findByAccountNumberForUpdate(transferRequest.getSourceAccount())
                    .orElseThrow(() -> new RuntimeException("Conta de origem não encontrada"));

            var destinationAccount = customerRepository.findByAccountNumberForUpdate(transferRequest.getDestinationAccount())
                    .orElseThrow(() -> new RuntimeException("Conta de destino não encontrada"));

            if (isTransactionAuthorized(sourceAccount, transferRequest)) {

                sourceAccount.setBalance(sourceAccount.getBalance() - transferRequest.getAmount());
                destinationAccount.setBalance(destinationAccount.getBalance() + transferRequest.getAmount());

                customerRepository.save(sourceAccount);
                customerRepository.save(destinationAccount);

                registerTransferHistory(transferRequest, "SUCCESS");
            } else {
                registerTransferHistory(transferRequest, "ERROR");
                throw new RuntimeException("Transação não autorizada");
            }

        } catch (Exception e) {
            registerTransferHistory(transferRequest, "ERROR");
            throw new RuntimeException("Erro ao processar a transferencia", e);
        }
    }

    @Override
    public List<Transfer> getTransferHistory(Integer accountNumber) {
        return transferConverter.toResponse(
                transferRepository.findBySourceAccountOrDestinationAccountOrderByTimestampAsc(accountNumber, accountNumber));
    }

    private void registerTransferHistory(TransferRequest transferRequest, String status) {
        transferRepository.save(transferConverter.toEntity(transferRequest, status));
    }

    private Boolean isTransactionAuthorized(CustomerEntity sourceAccount, TransferRequest transferRequest) {
        return transferRulesService.isAuthorizedToTransfer(sourceAccount, transferRequest.getAmount());
    }
}