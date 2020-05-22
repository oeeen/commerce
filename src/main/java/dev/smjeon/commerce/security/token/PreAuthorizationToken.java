package dev.smjeon.commerce.security.token;

import dev.smjeon.commerce.user.domain.Email;
import dev.smjeon.commerce.user.domain.Password;
import dev.smjeon.commerce.user.dto.UserLoginRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

public class PreAuthorizationToken extends UsernamePasswordAuthenticationToken {

    private PreAuthorizationToken(Email email, Password password) {
        super(email, password);
    }

    public PreAuthorizationToken(UserLoginRequest userLoginRequest) {
        this(userLoginRequest.getEmail(), userLoginRequest.getPassword());
    }

    public Email getEmail() {
        return (Email) super.getPrincipal();
    }

    public Password getUserPassword() {
        return (Password) super.getCredentials();
    }
}
