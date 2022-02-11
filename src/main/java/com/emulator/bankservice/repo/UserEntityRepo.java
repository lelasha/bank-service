package com.emulator.bankservice.repo;

import com.emulator.bankservice.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

import javax.persistence.LockModeType;
import java.util.Optional;

public interface UserEntityRepo extends JpaRepository<UserEntity, String> {

    Optional<UserEntity> findByCardNumber(String cardNumber);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    UserEntity save(UserEntity userEntity);

}
