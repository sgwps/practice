package ru.hse_se_podbel.data.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.hse_se_podbel.configuration.PasswordEncoderConfig;

import java.util.regex.Pattern;

public class PasswordValidator {
    @Autowired
    PasswordEncoderConfig passwordEncoderConfig;

    private final String passwordRegexp = "^[a-zA-Z0-9]{10,20}$";

    public final boolean validateUserPassword(String password) {
        return Pattern.matches(passwordRegexp, password);
    }

}
