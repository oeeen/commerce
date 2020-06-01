package dev.smjeon.commerce.security.token;

import dev.smjeon.commerce.security.UserContext;
import dev.smjeon.commerce.user.domain.UserRole;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.Collections;

public class SocialPostAuthorizationToken extends UsernamePasswordAuthenticationToken {
    public SocialPostAuthorizationToken(UserContext userContext) {
        super(userContext, userContext, parseAuthorities(userContext.getUserRole()));
    }

    private static Collection<? extends GrantedAuthority> parseAuthorities(UserRole role) {
        return Collections.singletonList(new SimpleGrantedAuthority(role.toString()));
    }
}
