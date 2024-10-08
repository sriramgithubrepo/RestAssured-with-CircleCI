package org.example.test.setup;
import java.util.regex.Pattern;

public class EmailValidator {

    private static final String EMAIL_PATTERN = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";

    public boolean isValid(String email) {
        return Pattern.compile(EMAIL_PATTERN).matcher(email).matches();
    }
}

