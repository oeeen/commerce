package dev.smjeon.commerce.user.dto;

import dev.smjeon.commerce.user.domain.Email;
import dev.smjeon.commerce.user.domain.Password;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class UserLoginRequest {
    private Email email;
    private Password password;

    public UserLoginRequest(Email email, Password password) {
        this.email = email;
        this.password = password;
    }
}
