package com.banking.model;
import com.banking.exception.InsufficientFundsException;
import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "accounts")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Version // keeps entity version to avoid conflicts with updates
    private Long version;

    @Column(nullable = false)
    private long userId;

    @Column(nullable = false, precision = 19, scale = 2) //max 19 digits in total including 2 digits after decimal point
    private BigDecimal balance;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AccountCurrency currency;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AccountStatus status;

    protected Account() { //uses for Hibernate to create Account entity
    }

    public Account(
            long userId,
            BigDecimal balance,
            AccountCurrency currency
    ) {
        validateUserId(userId);
        validateBalance(balance);
        validateCurrency(currency);

        this.userId = userId;
        this.balance = balance.setScale(2);
        this.currency = currency;
        this.status = AccountStatus.ACTIVE;
    }


    private void validateScale(BigDecimal value){
        if(value.stripTrailingZeros().scale() > 2) { //remove extra 0
            throw new IllegalArgumentException("Amount cannot have more than 2 decimal characters");
        }

    }

    //functions to checking data validate
    private void validateBalance(BigDecimal balance){
        if (balance == null || balance.signum() < 0){
            throw new IllegalArgumentException("Account balance cannot be negative");
        }
        validateScale(balance);
    }

    private void validateOperation(BigDecimal amount) {
        if(status != AccountStatus.ACTIVE) {
            throw new IllegalStateException("Account must be active");
        }
        if(amount == null || amount.signum() <= 0) {
            throw new IllegalArgumentException("Amount must be positive");
        }
        validateScale(amount);
    }

    private void validateUserId(long id){
        if(id <= 0){
            throw new IllegalArgumentException("ID must be positive");
        }
    }

    private void validateCurrency(AccountCurrency currency){
        if(currency == null){
            throw new IllegalArgumentException("Account currency cannot be null");
        }
    }



    //functions to manage account balance
    public void withdraw(BigDecimal amount){
        validateOperation(amount);

        if(balance.compareTo(amount) < 0){
            throw new InsufficientFundsException("Insufficient funds on account");
        }

        balance = balance.subtract(amount);
    }


    public void deposit(BigDecimal amount){
        validateOperation(amount);

        balance = balance.add(amount);

    }




    //functions to manage account status
    public void block(){
        if(status != AccountStatus.ACTIVE){
            throw new IllegalStateException("Account is not active");
        }
        status = AccountStatus.BLOCKED;
    }

    public void unblock(){
        if(status != AccountStatus.BLOCKED){
            throw new IllegalStateException("Account is not blocked");
        }
        status = AccountStatus.ACTIVE;
    }

    public void close(){
        if(status == AccountStatus.CLOSED){
            throw new IllegalStateException("Account is already closed");
        }

        if(balance.signum() != 0){
            throw new IllegalStateException("Account balance must be zero");
        }
        status = AccountStatus.CLOSED;
    }




    //getters
    public Long getId() {
        return id;
    }

    public long getUserId() {
        return userId;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public AccountCurrency getCurrency() {
        return currency;
    }

    public AccountStatus getStatus() {
        return status;
    }

}
