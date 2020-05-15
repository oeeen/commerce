package dev.smjeon.commerce.security.token;

import dev.smjeon.commerce.user.domain.Email;
import dev.smjeon.commerce.user.domain.Password;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

public class PreAuthorizationToken extends UsernamePasswordAuthenticationToken {

    public PreAuthorizationToken(Email email, Password password) {
        super(email, password.getValue());
    }

    public Email getEmail() {
        return (Email) super.getPrincipal();
    }

    public String getUserPassword() {
        return (String) super.getCredentials();
    }
}
