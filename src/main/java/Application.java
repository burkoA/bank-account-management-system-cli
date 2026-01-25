import exceptions.InvalidMenuOptionException;
import model.User;
import repositories.UserRepository;
import service.SystemUI;
import service.UserService;
import utils.JsonCreator;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Scanner;

public class Application {
    public static void main(String[] args) throws IOException {
        new Application().start();
    }

    private void start() {

        JsonCreator.createUsersJsonFiles();
        JsonCreator.createTransactionJsonFiles();

        UserRepository userRepository = new UserRepository();
        UserService userService = new UserService(userRepository);
        SystemUI.welcomeMessage();
        User user = new User();

        Scanner input = new Scanner(System.in, Charset.defaultCharset());
        String selectedOption = input.nextLine();

        switch(selectedOption) {
            case "1":
                user = loginFlow(input, userService);
                break;
            case "2":
                registrationFlow(input, userService);
                break;
            default:
                throw new InvalidMenuOptionException("Please select only available option!");
        }

        boolean isApplicationRunning = true;

        while(isApplicationRunning) {
            SystemUI.availableFunctionMessage();

            switch(input.nextLine()) {
                case "1":
                    break;
                case "2":
                    break;
                case "3":
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

    private static void registrationFlow(Scanner input, UserService controller){

        controller.registration(SystemUI.userCreation(input));
        System.out.println("User successfully created!");

        System.out.println("Do you want to login now? (yes / no)");
        String answer = input.nextLine();

        if (answer.equalsIgnoreCase("yes")) {
            loginFlow(input, controller);
        }
    }
}
