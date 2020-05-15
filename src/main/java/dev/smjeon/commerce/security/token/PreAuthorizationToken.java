package dev.smjeon.commerce.security.token;

import dev.smjeon.commerce.user.domain.Password;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

public class PreAuthorizationToken extends UsernamePasswordAuthenticationToken {

    public PreAuthorizationToken(String userName, Password password) {
        super(userName, password.getValue());
    }

    public String getUserName() {
        return (String) super.getPrincipal();
    }

    public String getUserPassword() {
        return (String) super.getCredentials();
    }
}
