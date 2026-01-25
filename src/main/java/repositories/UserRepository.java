package repositories;

import exceptions.IllegalCredentialsException;
import model.User;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserRepository {
    private static final String USERS_FILE = "users.json";

    private final ObjectMapper objectMapper = new ObjectMapper();
    private List<User> users;

    public UserRepository() {
        this.users = readUsers();
    }

    public void createUser(User user){
        users.add(user);
        writeUsers();
    }

    public void writeUsers() {
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

    public Optional<User> findByEmail(String email) {
        return users.stream()
                .filter(user -> user.getEmail().equals(email))
                .findFirst();
    }

    public int findUser(User user) {
        int position = users.indexOf(user);
        if(position == -1)
            throw new IllegalCredentialsException("User not found");

        return position;
    }

    public void updateUser(int position, User user) {
        users.set(position,user);
        writeUsers();
    }
}
