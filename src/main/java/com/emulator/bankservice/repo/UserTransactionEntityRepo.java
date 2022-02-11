package com.emulator.bankservice.repo;

import com.emulator.bankservice.entity.UserTransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.Instant;
import java.util.List;

public interface UserTransactionEntityRepo extends JpaRepository<UserTransactionEntity, String> {

    List<UserTransactionEntity> findAllByUserEntityAccountNumberAndTransactionDateBetween(String accountNumber,
                                                                                          Instant start,
                                                                                          Instant end);
}
