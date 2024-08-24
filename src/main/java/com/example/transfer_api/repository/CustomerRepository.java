package com.example.transfer_api.repository;

import com.example.transfer_api.entity.CustomerEntity;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<CustomerEntity, Long> {
    CustomerEntity findByAccountNumber(Integer accountNumber);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT c FROM CustomerEntity c WHERE c.accountNumber = :accountNumber")
    Optional<CustomerEntity> findByAccountNumberForUpdate(@Param("accountNumber") Integer accountNumber);
}