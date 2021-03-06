package com.zerobase.accountsys.domain;

import com.zerobase.accountsys.exception.AccountException;
import com.zerobase.accountsys.type.AccountStatus;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

import static com.zerobase.accountsys.type.ErrorCode.AMOUNT_EXCEED_BALANCE;
import static com.zerobase.accountsys.type.ErrorCode.INVALID_REQUEST;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Account extends BaseEntity {

    @ManyToOne
    private AccountUser accountUser;
    private String accountNumber;

    @Enumerated(EnumType.STRING)
    private AccountStatus accountStatus;
    private Long balance;

    private LocalDateTime registeredAt;
    private LocalDateTime unRegisteredAt;

    public void useBalance(Long amount) {
        if(amount > balance) {
            throw new AccountException(AMOUNT_EXCEED_BALANCE);
        }
        balance -= amount;
    }

    public void cancelBalance(Long amount) {
        if(amount < 0) {
            throw new AccountException(INVALID_REQUEST);
        }
        balance += amount;
    }

}
