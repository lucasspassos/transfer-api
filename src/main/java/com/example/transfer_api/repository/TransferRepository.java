package com.example.transfer_api.repository;

import com.example.transfer_api.entity.TransferEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransferRepository extends JpaRepository<TransferEntity, Long> {
    List<TransferEntity> findBySourceAccountOrDestinationAccountOrderByTimestampAsc(Integer sourceAccount, Integer destinationAccount);
}