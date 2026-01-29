package view;

import model.Transaction;
import model.User;
import utils.PasswordHashing;

import java.util.List;
import java.util.Scanner;

public class SystemUI {
    public static void welcomeMessage() {
        System.out.println("Welcome to the Bank Account Management System!");
        System.out.println("In this command line program you can transfer, deposit, withdraw your money!");
        System.out.println("For log in press - 1 \t For registration press - 2");
    }

    public static User userCreation(Scanner input) {
        User user = new User();

        System.out.println("Enter user name");
        user.setName(input.nextLine());

        System.out.println("Enter email");
        user.setEmail(input.nextLine());

        System.out.println("Enter password");
        user.setPassword(PasswordHashing.hashPassword(input.nextLine()));

        return user;
    }

    public static void loginMessage() {
        System.out.println("Enter credentials in this order. If you failed password 3 times account will be blocked");
        System.out.println("1. Email");
        System.out.println("2. Password");
    }

    public static void successfulLogin(String name) {
        System.out.println("Welcome " + name +"!");
        System.out.println("We are happy that you using our system!");
    }

    public static void availableFunctionMessage() {
        System.out.println("Select next option -");
        System.out.println("1. Deposit");
        System.out.println("2. Withdraw");
        System.out.println("3. Transfer");
        System.out.println("4. Show all transactions");
        System.out.println("5. Show given number of transactions");
        System.out.println("6. Exit");
    }

    public static void exitMessage(String name) {
        System.out.println("Thank you for using our system " + name);
        System.out.println("Have a nice day! Bye!");
    }

    public static void depositMessage() {
        System.out.println("Write the value of the deposit");
    }

    public static void successfulDeposit() {
        System.out.println("Deposit is successfully!");
        System.out.println("Thank you for using our system!");
    }

    public static void withdrawMessage() {
        System.out.println("Write the value of the withdraw");
    }

    public static void successfulWithdraw() {
        System.out.println("Withdraw is successful!");
        System.out.println("Thank you for using our system!");
    }

    public static void transferMessage() {
        System.out.println("Write required information in provided order");
        System.out.println("1. Email \t 2. Amount");
    }

    public static void successfulTransfer() {
        System.out.println("Transfer was successful!");
        System.out.println("Thank you for using our system!");
    }

    public static void printUserTransaction(List<Transaction> transactions) {
        for(Transaction transaction : transactions) {
            System.out.println(transaction.toString());
        }
    }

    public static void limitTransactionMessage() {
        System.out.println("Write how many transaction you want to see");
    }
}
