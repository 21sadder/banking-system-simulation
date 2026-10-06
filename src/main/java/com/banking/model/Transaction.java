import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;

public class Transaction {
    private final long id;
    private final long fromAccountId;
    private final long toAccountId;
    private final BigDecimal amount;
    private final TransactionType type;
    private TransactionStatus status;
    private final Instant createdAt;


    public Transaction(
            long id,
            long fromAccountId,
            long toAccountId,
            BigDecimal amount,
            TransactionType type
    ){
        validateAccounts(fromAccountId, toAccountId);
        validateAmount(amount);
        validateType(type);

        this.id = id;
        this.fromAccountId = fromAccountId;
        this.toAccountId = toAccountId;
        this.amount = amount;
        this.type = type;
        this.status = TransactionStatus.PENDING;
        this.createdAt = Instant.now();
    }

    private void validateAccounts(long fromAccountId, long toAccountId) {
        if (fromAccountId <= 0 || toAccountId <= 0) {
            throw new IllegalArgumentException("com.banking.model.Account IDs must be positive");
        }

        if (fromAccountId == toAccountId) {
            throw new IllegalArgumentException("Sender account and receiver account must be different");
        }
    }

    private void validateAmount(BigDecimal amount) {
        if (amount == null || amount.signum() <= 0) {
            throw new IllegalArgumentException("Amount must be positive");
        }
    }

    private void validateType(TransactionType type) {
        if (type == null) {
            throw new IllegalArgumentException("Transaction type cannot be null");
        }
    }

    private void ensurePending() {
        if (status != TransactionStatus.PENDING) {
            throw new IllegalStateException("Transaction has already been processed");
        }
    }

    public void markCompleted() {
        ensurePending();
        this.status = TransactionStatus.COMPLETED;
    }

    public void markFailed() {
        ensurePending();
        this.status = TransactionStatus.FAILED;
    }

    public long getId() {
        return id;
    }

    public long getFromAccountId() {
        return fromAccountId;
    }

    public long getToAccountId() {
        return toAccountId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public TransactionType getType() {
        return type;
    }

    public TransactionStatus getStatus() {
        return status;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }
}



