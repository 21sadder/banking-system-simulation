package com.banking.service;

import com.banking.dto.TransactionDto;
import com.banking.model.Account;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TransferService {

    private final AccountService accountService;

    public TransferService(AccountService accountService) {
        this.accountService = accountService;
    }


    @Transactional
    public void transfer(TransactionDto dto) {
        Long fromId = dto.getFromAccountId();
        Long toId = dto.getToAccountId();

        if(fromId == null || toId == null
                || fromId <= 0 || toId <= 0) {
            throw new IllegalArgumentException(
                    "Account ids must be positive"
            );
        }
        if(fromId.equals(toId)) {
            throw new IllegalArgumentException(
                    "Cannot transfer to the same account"
            );
        }

        Account fromAccount = accountService.getAccount(fromId);
        Account toAccount = accountService.getAccount(toId);

        if(fromAccount.getCurrency() != toAccount.getCurrency()) {
            throw new IllegalArgumentException(
                    "Accounts must have the same currency"
            );
        }

        fromAccount.withdraw(dto.getAmount());
        toAccount.deposit(dto.getAmount());


    }


}
