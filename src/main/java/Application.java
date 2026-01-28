import exceptions.InvalidMenuOptionException;
import model.User;
import repositories.TransactionRepository;
import repositories.UserRepository;
import service.TransactionService;
import view.SystemUI;
import service.UserService;
import utils.JsonCreator;

import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.util.Scanner;

public class Application {
    public static void main(String[] args) {
        new Application().start();
    }

    private void start() {

        JsonCreator.createUsersJsonFiles();
        JsonCreator.createTransactionJsonFiles();

        UserRepository userRepository = new UserRepository();
        UserService userService = new UserService(userRepository);
        TransactionRepository transactionRepository = new TransactionRepository();
        TransactionService transactionService = new TransactionService(transactionRepository);

        SystemUI.welcomeMessage();
        User user;

        Scanner input = new Scanner(System.in, Charset.defaultCharset());
        String selectedOption = input.nextLine();

        switch(selectedOption) {
            case "1":
                user = loginFlow(input, userService);
                break;
            case "2":
                user = registrationFlow(input, userService);
                break;
            default:
                throw new InvalidMenuOptionException("Please select only available option!");
        }

        if(user == null)
            System.exit(1);

        boolean isApplicationRunning = true;

        while(isApplicationRunning) {
            SystemUI.availableFunctionMessage();

            switch(input.nextLine()) {
                case "1":
                    SystemUI.depositMessage();

                    try {
                        BigDecimal amount = new BigDecimal(input.nextLine());

                        userService.deposit(user, amount);
                        transactionService.transactionDeposit(user, amount);

                    } catch (NumberFormatException e) {
                        System.out.println(e.getMessage());
                    }

                    SystemUI.successfulDeposit();
                    break;
                case "2":
                    SystemUI.withdrawMessage();

                    try {
                        BigDecimal amount = new BigDecimal(input.nextLine());

                        userService.withdraw(user, amount);
                        transactionService.transactionWithdraw(user, amount);

                    } catch (NumberFormatException e) {
                        System.out.println(e.getMessage());
                    }

                    SystemUI.successfulWithdraw();
                    break;
                case "3":
                    SystemUI.transferMessage();

                    try {

                        String sendToEmail = input.nextLine();
                        BigDecimal amount = new BigDecimal(input.nextLine());

                        userService.transfer(user, sendToEmail, amount);
                        transactionService.transactionTransfer(user, sendToEmail, amount);

                    } catch (NumberFormatException e) {
                        System.out.println(e.getMessage());
                    }

                    SystemUI.successfulTransfer();
                    break;
                case "4":
                    isApplicationRunning = false;
                    break;
                default:
            }

        }

        SystemUI.exitMessage(user.getName());

        input.close();
    }

    private static User loginFlow(Scanner input, UserService controller) {

        SystemUI.loginMessage();

        String email = input.nextLine();
        String password = input.nextLine();


        User user = controller.login(email,password);

        SystemUI.successfulLogin(user.getName());

        return user;
    }

    private static User registrationFlow(Scanner input, UserService controller){

        controller.registration(SystemUI.userCreation(input));
        System.out.println("User successfully created!");

        System.out.println("Do you want to login now? (yes / no)");
        String answer = input.nextLine();

        if (answer.equalsIgnoreCase("yes")) {
            return loginFlow(input, controller);
        } else {
            return null;
        }
    }
}
