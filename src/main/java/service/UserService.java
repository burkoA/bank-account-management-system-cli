package service;

import exceptions.IllegalCredentialsException;
import model.User;
import repositories.UserRepository;

public class UserService {
    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User login(String email, String password) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalCredentialsException("Email is incorrect!"));

        if(!userRepository.passwordValidation(user.getPassword(), password)) {
            throw new IllegalCredentialsException("Password is incorrect");
        }

        return user;
    }

    public void registration(User user){
        userRepository.createUser(user);
    }

    public void deposit() {

    }

    public void withdraw() {

    }

    public void transfer() {

    }
}
