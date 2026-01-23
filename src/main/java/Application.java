import exceptions.IllegalCredentialsException;
import exceptions.IllegalOptionException;
import model.User;
import repositories.UserRepository;
import service.SystemUI;
import utils.JsonCreator;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Scanner;

public class Application {
    public static void main(String[] args) throws IOException {

        JsonCreator.createUsersJsonFiles();
        JsonCreator.createTransactionJsonFiles();

        UserRepository userRepository = new UserRepository();
        SystemUI.welcomeMessage();
        User user;

        Scanner input = new Scanner(System.in, Charset.defaultCharset());
        String selectedOption = input.nextLine();
        String email;
        String password;

        switch(selectedOption) {
            case "1":
                loginFlow(input, userRepository);
                break;
            case "2":
                registrationFlow(input, userRepository);
                break;
            default:
                throw new IllegalOptionException("Please select only available option!");
        }

        input.close();
    }

    private static void loginFlow(Scanner input, UserRepository repository) {

        SystemUI.loginMessage();

        String email = input.nextLine();
        String password = input.nextLine();

        User user = repository.checkEmail(email);
        repository.checkPassword(user.getPassword(), password);

        SystemUI.successfulLogin(user.getName());
    }

    private static void registrationFlow(Scanner input, UserRepository repository) throws IOException {

        repository.createUser(SystemUI.userCreation(input));
        System.out.println("User successfully created!");

        System.out.println("Do you want to login now? (yes / no)");
        String answer = input.nextLine();

        if (answer.equalsIgnoreCase("yes")) {
            loginFlow(input, repository);
        }
    }
}
