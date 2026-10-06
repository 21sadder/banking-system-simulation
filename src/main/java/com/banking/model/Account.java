
import java.math.BigDecimal;

public class Account {

    private final long id;
    private final long userId;

    private BigDecimal balance;
    private final AccountCurrency currency;
    private AccountStatus status;


    public Account(
            long id,
            long userId,
            BigDecimal balance,
            AccountCurrency currency
            ){

        validateId(id);
        validateId(userId);
        validateBalance(balance);
        validateCurrency(currency);

        this.id = id;
        this.userId = userId;
        this.balance = balance;
        this.currency = currency;
        this.status = AccountStatus.ACTIVE;
    }


    //functions to checking data validate
    private void validateBalance(BigDecimal balance){
        if (balance == null || balance.signum() < 0){
            throw new IllegalArgumentException("Account balance cannot be negative");
        }

    }

    private void validateOperation(BigDecimal amount) {
        if(status != AccountStatus.ACTIVE) {
            throw new IllegalStateException("Account must be active");
        }
        if(amount == null || amount.signum() <= 0) {
            throw new IllegalArgumentException("Amount must be positive");
        }
    }

    private void validateId(long id){
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
    public long getId() {
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
