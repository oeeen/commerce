package dev.smjeon.commerce.security.token;

import dev.smjeon.commerce.user.domain.Email;
import dev.smjeon.commerce.user.domain.Password;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

public class PreAuthorizationToken extends UsernamePasswordAuthenticationToken {

    public PreAuthorizationToken(Email email, Password password) {
        super(email, password);
    }

    public Email getEmail() {
        return (Email) super.getPrincipal();
    }

    public Password getUserPassword() {
        return (Password) super.getCredentials();
    }
}
