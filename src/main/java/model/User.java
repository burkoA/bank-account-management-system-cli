package model;

import exceptions.BalanceException;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import exceptions.IllegalCredentialsException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@JsonPropertyOrder({"id","name","email","password","balance"})
public class User {
    private UUID id;
    private String email;
    private String name;
    private String password;
    private double balance;

    public User() {
        this.id = UUID.randomUUID();
    }

    public User(String email, String name, String password, double balance) {
        this.id = UUID.randomUUID();
        this.email = email;
        this.name = name;
        this.password = password;
        this.balance = balance;
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

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        if(balance < 0) {
            throw new BalanceException("Balance can't be lower than 0");
        }

        this.balance = balance;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        if(email == null || email.isBlank())
            throw new IllegalCredentialsException("Email couldn't be null");

        this.email = email;
    }
}
