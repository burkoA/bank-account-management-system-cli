package repositories;

import exceptions.IllegalCredentialsException;
import model.User;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UserRepository {
    private static final String USERS_FILE = "users.json";

    private final ObjectMapper objectMapper = new ObjectMapper();
    private List<User> users;

    public UserRepository() {
        this.users = readUsers();
    }

    public void createUser(User user) throws IOException {
        users.add(user);

        objectMapper.writerWithDefaultPrettyPrinter()
                .writeValue(new File(USERS_FILE), users);
    }

    public List<User> readUsers() {
        File file = new File(USERS_FILE);

        if (!file.exists() || file.length() == 0) {
            return new ArrayList<>();
        }

        return objectMapper.readValue(file, new TypeReference<List<User>>() {});
    }

    public User checkEmail(String email) {
        for(User user : users) {
            if(user.getEmail().equals(email))
                return user;
        }

        throw new IllegalCredentialsException("Provided email doesn't exist");
    }

    public void checkPassword(String correctPassword, String providedPassword) {
        if(!correctPassword.equals(providedPassword)) {
            throw new IllegalCredentialsException("Provided password is incorrect");
        }
    }

    private void reload() {
        users = readUsers();
    }
}
