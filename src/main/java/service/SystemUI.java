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
}
