import exceptions.BalanceException;
import exceptions.IllegalCredentialsException;
import model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import repositories.UserRepository;
import service.UserService;
import utils.PasswordHashing;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setEmail("test@gmail.com");
        user.setName("Test user");
        user.setPassword(PasswordHashing.hashPassword("secret"));
        user.setBalance(new BigDecimal("100.00"));
    }

    @Test
    @DisplayName("Login succeeds when email exists")
    void login_successful() {
        when(userRepository.findByEmail(user.getEmail()))
                .thenReturn(Optional.of(user));

        User loggedUser = userService.login("test@gmail.com");

        assertThat(loggedUser).isEqualTo(user);
    }

    @Test
    @DisplayName("Login fails when email does not exists")
    void login_emailNotFound_throwsException() {
        when(userRepository.findByEmail("wrong@gmail.com"))
                .thenReturn(Optional.empty());

        assertThatThrownBy(() -> userService.login("wrong@gmail.com"))
                .isInstanceOf(IllegalCredentialsException.class)
                .hasMessageContaining("Email is incorrect");
    }

    @Test
    @DisplayName("Password verification succeeds for correct password")
    void verifyPassword_correctPassword() {
        boolean result = userService.verifyPassword("secret", user.getPassword());

        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("Password verification fails for incorrect password")
    void verifyPassword_wrongPassword() {
        boolean result = userService.verifyPassword("wrong", user.getPassword());

        assertThat(result).isFalse();
    }

    @Test
    @DisplayName("Deposit increases user balance")
    void deposit_success() {
        when(userRepository.findUser(user)).thenReturn(0);

        userService.deposit(user, new BigDecimal("50.00"));

        assertThat(user.getBalance()).isEqualByComparingTo("150.00");
        verify(userRepository).updateUser(0,user);
    }

    @Test
    @DisplayName("Withdraw decreases user balance")
    void withdraw_success() {
        when(userRepository.findUser(user)).thenReturn(0);

        userService.withdraw(user, new BigDecimal("50.00"));

        assertThat(user.getBalance()).isEqualByComparingTo("50.00");
    }

    @Test
    @DisplayName("Withdraw fails when balance is insuffiecient")
    void withdraw_insufficientBalance_throwsException() {
        assertThatThrownBy(() ->
                userService.withdraw(user, new BigDecimal("200.00")))
                .isInstanceOf(BalanceException.class);
    }

    @Test
    @DisplayName("Transfer decreases sender balance and increases receiver balance")
    void transfer_success() {
        User receiver = new User();
        receiver.setEmail("receiver@gmail.com");
        receiver.setBalance(new BigDecimal("50.00"));

        when(userRepository.findByEmail("receiver@gmail.com"))
                .thenReturn(Optional.of(receiver));
        when(userRepository.findUser(user)).thenReturn(0);
        when(userRepository.findUser(receiver)).thenReturn(1);

        userService.transfer(user,"receiver@gmail.com", new BigDecimal("30.00"));

        assertThat(user.getBalance()).isEqualByComparingTo("70.00");
        assertThat(receiver.getBalance()).isEqualByComparingTo("80.00");

        verify(userRepository).updateUser(0,user);
        verify(userRepository).updateUser(1,receiver);
    }

    @Test
    @DisplayName("Transfer to self throw exception")
    void transfer_toSelf_throwsException() {
        when(userRepository.findByEmail(user.getEmail()))
                .thenReturn(Optional.of(user));

        assertThatThrownBy(() ->
                userService.transfer(user, user.getEmail(), new BigDecimal("10")))
                .isInstanceOf(BalanceException.class)
                .hasMessageContaining("Cannot transfer to yourself");
    }

    @Test
    @DisplayName("Transfer fails when sender has insuffiecient balance")
    void transfer_insufficientBalance_throwsException() {
        User receiver = new User();
        receiver.setEmail("receiver@gmail.com");

        when(userRepository.findByEmail("receiver@gmail.com"))
                .thenReturn(Optional.of(receiver));

        assertThatThrownBy(() ->
                userService.transfer(user, "receiver@gmail.com", new BigDecimal("500")))
                .isInstanceOf(BalanceException.class)
                .hasMessageContaining("Not enough money");
    }

    @Test
    @DisplayName("Remaining login attempts calculated corerctly")
    void getRemainAttempt_returnsCorrectValue() {
        assertThat(userService.getRemainAttempt(0)).isEqualTo(3);
        assertThat(userService.getRemainAttempt(1)).isEqualTo(2);
        assertThat(userService.getRemainAttempt(2)).isEqualTo(1);
        assertThat(userService.getRemainAttempt(3)).isEqualTo(0);
    }

    @Test
    @DisplayName("Block user sets lock flag to true")
    void blockUser_setsUserLockToTrue() {
        when(userRepository.findUser(user))
                .thenReturn(0);

        userService.blockUser(user);

        assertThat(user.getLock()).isTrue();
        verify(userRepository).updateUser(0,user);
    }
}
