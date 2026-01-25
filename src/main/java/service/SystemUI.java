package service;

import model.User;

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
        user.setPassword(input.nextLine());

        return user;
    }

    public static void loginMessage() {
        System.out.println("Enter credentials in this order");
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
        System.out.println("4. Exit");
    }

    public static void exitMessage(String name) {
        System.out.println("Thank you for using our system " + name);
        System.out.println("Have a nice day! Bye!");
    }
}
