package dev.smjeon.commerce.security.providers;

import dev.smjeon.commerce.security.exception.UnauthorizedException;
import dev.smjeon.commerce.security.token.SocialPreAuthorizationToken;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

public class SocialLoginAuthenticationProvider implements AuthenticationProvider {

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        SocialPreAuthorizationToken token = (SocialPreAuthorizationToken) authentication;
        String code = token.getCode();

        throw new UnauthorizedException();
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return SocialPreAuthorizationToken.class.isAssignableFrom(authentication);
    }
}
