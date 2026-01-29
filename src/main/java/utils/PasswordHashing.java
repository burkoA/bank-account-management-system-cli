package utils;

import exceptions.IllegalCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordHashing {

    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public static String hashPassword(String plainPassword) {
        if(plainPassword == null || plainPassword.isBlank()) {
            throw new IllegalCredentialsException("Password cannot be null or empty");
        }
        return encoder.encode(plainPassword);
    }

    public static boolean verifyPassword(String plainPassword, String hashPassword) {
        return encoder.matches(plainPassword,hashPassword);
    }
}
