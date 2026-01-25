package model;

import exceptions.BalanceException;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import exceptions.IllegalCredentialsException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.regex.Pattern;

@JsonPropertyOrder({"id","name","email","password","balance"})
public class User {
    private UUID id;
    private String email;
    private String name;
    private String password;
    private BigDecimal balance;

    public User() {
        this.id = UUID.randomUUID();
    }

    public User(String email, String name, String password, BigDecimal balance) {
        this.id = UUID.randomUUID();
        setEmail(email);
        setName(name);
        setPassword(password);
        setBalance(balance);
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if(name == null || name.isBlank())
            throw new IllegalCredentialsException("Name couldn't be null");

        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        if(password == null || password.isBlank())
            throw new IllegalCredentialsException("Password couldn't be null");

        this.password = password;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        if(balance.compareTo(BigDecimal.ZERO) < 0) {
            throw new BalanceException("Balance can't be lower than 0");
        }

        this.balance = balance;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        if(email == null || !emailValidation(email))
            throw new IllegalCredentialsException("Email couldn't be null");

        this.email = email;
    }

    private boolean emailValidation(String email) {
        String regexPattern = "^(.+)@(\\S+)$";
        return Pattern.compile(regexPattern)
                .matcher(email)
                .matches();
    }

    @Override
    public boolean equals(Object object) {
        if(!(object instanceof User))
            return false;

        User user = (User) object;

        return Objects.equals(this.id, user.id);
    }

    @Override
    public int hashCode(){
        return Objects.hash(this.id);
    }
}
