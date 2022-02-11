package com.emulator.bankservice.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Version;
import java.math.BigDecimal;

/**
 * for simplicity there is one card per account,
 * and no currency convention
 */
@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity {
    @Id
    private String accountNumber;
    private BigDecimal balance;
    private String cardNumber;
    private String PIN;
    private String fingerPrint;
    @Version
    private Integer version;
}
