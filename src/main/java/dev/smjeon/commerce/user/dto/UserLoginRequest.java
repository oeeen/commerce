package dev.smjeon.commerce.user.dto;

import dev.smjeon.commerce.user.domain.Email;
import dev.smjeon.commerce.user.domain.Password;
import lombok.Getter;

@Getter
public class UserLoginRequest {
    private final Email email;
    private final Password password;

    public UserLoginRequest(Email email, Password password) {
        this.email = email;
        this.password = password;
    }
}
