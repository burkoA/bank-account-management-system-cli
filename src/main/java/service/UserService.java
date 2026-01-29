package service;

import exceptions.BalanceException;
import exceptions.IllegalCredentialsException;
import model.User;
import repositories.UserRepository;
import utils.PasswordHashing;

import java.math.BigDecimal;
import java.util.regex.Pattern;

public class UserService {
    private final UserRepository userRepository;
    public static final int MAX_PASSWORD_TRY = 3;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User login(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalCredentialsException("Email is incorrect!"));
    }

    public boolean verifyPassword(String plainPassword, String hashPassword) {
        return PasswordHashing.verifyPassword(plainPassword, hashPassword);
    }

    public int getRemainAttempt(int tries) {
        return MAX_PASSWORD_TRY - tries;
    }

    public void registration(User user) {
        userRepository.findByEmail(user.getEmail())
                .ifPresent(u -> {
                    throw new IllegalCredentialsException("Email already exists");
                });

        if (!emailValidation(user.getEmail()))
            throw new IllegalCredentialsException("Wrong email type");

        userRepository.createUser(user);
    }

    public void deposit(User user, BigDecimal amount) {
        validateAmount(amount);

        int position = userRepository.findUser(user);
        user.setBalance(user.getBalance().add(amount));

        userRepository.updateUser(position, user);

    }

    public void withdraw(User user, BigDecimal amount) {
        validateAmount(amount);

        if (user.getBalance().compareTo(amount) < 0)
            throw new BalanceException("Don't enough money");

        int position = userRepository.findUser(user);
        user.setBalance(user.getBalance().subtract(amount));

        userRepository.updateUser(position, user);
    }

    public void transfer(User senderUser, String email, BigDecimal amount) {
        validateAmount(amount);

        User receiverUser = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalCredentialsException("Provided email doesn't exists!"));

        if (senderUser.equals(receiverUser))
            throw new BalanceException("Cannot transfer to yourself");

        if (senderUser.getBalance().compareTo(amount) < 0)
            throw new BalanceException("Not enough money for transfer");

        int receiverPosition = userRepository.findUser(receiverUser);
        int senderPosition = userRepository.findUser(senderUser);

        senderUser.setBalance(senderUser.getBalance().subtract(amount));
        receiverUser.setBalance(receiverUser.getBalance().add(amount));

        userRepository.updateUser(receiverPosition, receiverUser);
        userRepository.updateUser(senderPosition, senderUser);

    }

    public void blockUser(User user) {
        int userPosition = userRepository.findUser(user);
        user.setLock(true);
        userRepository.updateUser(userPosition, user);
    }

    private void validateAmount(BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new BalanceException("Amount couldn't be smaller or equal 0");
        }
    }

    private boolean emailValidation(String email) {
        String regexPattern = "^(.+)@(\\S+)$";
        return Pattern.compile(regexPattern)
                .matcher(email)
                .matches();
    }


}
