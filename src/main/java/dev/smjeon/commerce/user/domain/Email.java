package dev.smjeon.commerce.user.domain;

import dev.smjeon.commerce.user.exception.InvalidEmailException;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@NoArgsConstructor
@Getter
@Embeddable
public class Email {
    
    @Column(nullable = false)
    private String email;

    private static final String PATTERN = "[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}";
    private final Pattern pattern = Pattern.compile(PATTERN);

    public Email(String email) {
        validate(email);
        this.email = email;
    }

    private void validate(String email) {
        Matcher matcher = pattern.matcher(email);

        if (!matcher.matches()) {
            throw new InvalidEmailException(email);
        }
    }
}
