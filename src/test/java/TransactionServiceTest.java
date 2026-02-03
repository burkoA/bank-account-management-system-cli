import model.Transaction;
import model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import repositories.TransactionRepository;
import service.TransactionService;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class TransactionServiceTest {
    @Mock
    private TransactionRepository transactionRepository;

    @InjectMocks
    private TransactionService transactionService;

    private User user;

    @BeforeEach
    void setUp() {
        user = new User();

        user.setEmail("test@gmail.com");
    }

    @Test
    @DisplayName("Deposit transaction is created")
    void transactionDeposit_createsTransaction() {
        transactionService.transactionDeposit(user, new BigDecimal("20"));

        verify(transactionRepository).createTransaction(any(Transaction.class));
    }

    @Test
    @DisplayName("Withdraw transaction is created")
    void transactionWithdraw_createsTransaction() {
        transactionService.transactionWithdraw(user, new BigDecimal("10"));

        verify(transactionRepository).createTransaction(any(Transaction.class));
    }

    @Test
    @DisplayName("Transfer transaction is created")
    void transactionTransfer_createTransaction() {
        transactionService.transactionTransfer(user, "receiver@gmail.com", new BigDecimal("30"));

        verify(transactionRepository).createTransaction(any(Transaction.class));
    }
}
