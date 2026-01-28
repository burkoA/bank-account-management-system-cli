package model;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import enums.OperationType;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@JsonPropertyOrder({"id","type","fromUser","toUser","amount","timestamp"})
public class Transaction {

    private UUID id;
    private OperationType operationType;
    private String fromUser;
    private String toUser;
    private BigDecimal amount;
    private LocalDateTime timestamp;

    public Transaction() {
        this.id = UUID.randomUUID();
    }

    public UUID getId() {
        return this.id;
    }

    public OperationType getOperationType() {
        return operationType;
    }

    public void setOperationType(OperationType operationType) {
        this.operationType = operationType;
    }

    public String getFromUser() {
        return fromUser;
    }

    public void setFromUser(String fromUser) {
        this.fromUser = fromUser;
    }

    public String getToUser() {
        return toUser;
    }

    public void setToUser(String toUser) {
        this.toUser = toUser;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
