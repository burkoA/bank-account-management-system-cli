package model;

import java.time.LocalDateTime;

public record Transaction(String fromUser, String toUser, double amount, LocalDateTime transactionTime) {

}
