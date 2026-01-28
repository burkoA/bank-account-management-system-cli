package service;

import enums.OperationType;
import model.Transaction;
import model.User;
import repositories.TransactionRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class TransactionService {
    private TransactionRepository transactionRepository;

    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public void transactionDeposit(User user, BigDecimal amount) {
        Transaction transaction = createTransaction(OperationType.DEPOSIT,
                user.getEmail(), null, amount);

        transactionRepository.createTransaction(transaction);
    }

    public void transactionWithdraw(User user, BigDecimal amount) {
        Transaction transaction = createTransaction(OperationType.WITHDRAW,
                user.getEmail(), null, amount);

        transactionRepository.createTransaction(transaction);
    }

    public void transactionTransfer(User user, String sendToEmail, BigDecimal amount) {
        Transaction transaction = createTransaction(OperationType.TRANSFER,
                user.getEmail(), sendToEmail, amount);

        transactionRepository.createTransaction(transaction);
    }

    private Transaction createTransaction(OperationType type, String fromUser, String toUser, BigDecimal amount){
        Transaction transaction = new Transaction();
        transaction.setOperationType(type);
        transaction.setFromUser(fromUser);
        transaction.setToUser(toUser);
        transaction.setAmount(amount);
        transaction.setTimestamp(LocalDateTime.now());

        return transaction;
    }
}
