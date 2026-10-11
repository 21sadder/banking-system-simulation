package com.banking.service;
import com.banking.exception.AccountNotFoundException;
import com.banking.model.Account;
import com.banking.repository.AccountRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
@Transactional
public class AccountService {

    private final AccountRepository accountRepository;

    public AccountService(AccountRepository accountRepository){
        this.accountRepository = accountRepository;
    }

    @Transactional(readOnly = true)
    public Account getAccount(Long id){
        return accountRepository.findById(id)
                .orElseThrow(() ->
                        new AccountNotFoundException("Account was not found")
                );
    }

    @Transactional(readOnly = true)
    public List<Account> getUserAccounts(long userId){
        if(userId <= 0){
            throw new IllegalArgumentException("User id must be positive");
        }

        return accountRepository.findByUserId(userId);
    }

    public Account createAccount(Account account){
       if(account == null){
           throw new IllegalArgumentException("Account cannot be null");
       }

       return accountRepository.save(account);
    }

    public Account deposit(Long accountId, BigDecimal amount){
        Account account = getAccount(accountId);
        account.deposit(amount);
        return accountRepository.save(account);
    }

    public Account withdraw(Long accountId, BigDecimal amount){
        Account account = getAccount(accountId);
        account.withdraw(amount);
        return accountRepository.save(account);
    }

     public Account blockAccount(Long accountId){
        Account account = getAccount(accountId);
        account.block();
        return accountRepository.save(account);
     }

     public Account unblockAccount(Long accountId){
        Account account = getAccount(accountId);
        account.unblock();
        return accountRepository.save(account);
     }

     public Account closeAccount(Long accountId){
        Account account = getAccount(accountId);
        account.close();
        return accountRepository.save(account);
     }
}
