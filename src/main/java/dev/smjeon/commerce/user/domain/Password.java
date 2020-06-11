package dev.smjeon.commerce.user.domain;

import dev.smjeon.commerce.user.exception.InvalidPasswordException;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Transient;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@NoArgsConstructor
@Getter
@Embeddable
public class Password {
    private static final String PATTERN = ".*(?=^.{8,}$)(?=.*\\d)(?=.*[a-zA-Z])(?=.*[!@#$%^&+=]).*";

    @Transient
    private final Pattern pattern = Pattern.compile(PATTERN);

    @Column(nullable = false, name = "password")
    private String value;

    public Password(String password) {
        validate(password);
        this.value = password;
    }

    private void validate(String password) {
        Matcher matcher = pattern.matcher(password);

        if (!matcher.matches()) {
            throw new InvalidPasswordException(password);
        }
    }
}
