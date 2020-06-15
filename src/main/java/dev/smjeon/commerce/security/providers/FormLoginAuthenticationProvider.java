package dev.smjeon.commerce.security.providers;

import dev.smjeon.commerce.security.exception.UnauthorizedException;
import dev.smjeon.commerce.security.token.PostAuthorizationToken;
import dev.smjeon.commerce.security.token.PreAuthorizationToken;
import dev.smjeon.commerce.user.application.UserService;
import dev.smjeon.commerce.user.domain.Email;
import dev.smjeon.commerce.user.domain.Password;
import dev.smjeon.commerce.user.domain.User;
import dev.smjeon.commerce.user.exception.NotFoundUserException;
import dev.smjeon.commerce.user.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Component
public class FormLoginAuthenticationProvider implements AuthenticationProvider {

    private UserService userService;
    private UserRepository userRepository;

    public FormLoginAuthenticationProvider(UserService userService, UserRepository userRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        PreAuthorizationToken token = (PreAuthorizationToken) authentication;

        Email email = token.getEmail();
        Password password = token.getUserPassword();

        User user = userRepository.findByEmail(email).orElseThrow(() -> new NotFoundUserException(email.getEmail()));

        if (userService.isCorrectPassword(password, user) && userService.isActiveUser(user)) {
            return new PostAuthorizationToken(userService.findByEmail(email));
        }

        throw new UnauthorizedException();
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return PreAuthorizationToken.class.isAssignableFrom(authentication);
    }

}
