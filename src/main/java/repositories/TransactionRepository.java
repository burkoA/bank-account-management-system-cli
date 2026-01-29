package repositories;

import model.Transaction;
import model.User;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class TransactionRepository {

    private static final String TRANSACTION_FILE = "transaction.json";
    private final ObjectMapper objectMapper = new ObjectMapper();
    private List<Transaction> transactions;

    public TransactionRepository() {
        this.transactions = readTransaction();
    }

    private List<Transaction> readTransaction() {
        File file = new File(TRANSACTION_FILE);

        if (!file.exists() || file.length() == 0) {
            return new ArrayList<>();
        }

        return objectMapper.readValue(file, new TypeReference<List<Transaction>>() {});
    }

    private void writeTransactions() {
        objectMapper.writerWithDefaultPrettyPrinter()
                .writeValue(new File(TRANSACTION_FILE), transactions);
    }


    public void createTransaction(Transaction transaction) {
        transactions.add(transaction);
        writeTransactions();
    }

    public List<Transaction> findTransaction(String userEmail) {
        return transactions.stream()
                .filter(transaction -> transaction.getFromUser().equals(userEmail))
                .toList();
    }

    public List<Transaction> findLimitCountOfTransaction(String email, long limit) {
        return transactions.stream()
                .filter(transaction -> transaction.getFromUser().equals(email))
                .limit(limit)
                .toList();
    }
}
