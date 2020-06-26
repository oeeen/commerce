package dev.smjeon.commerce.security.providers;

import dev.smjeon.commerce.security.exception.UnauthorizedException;
import dev.smjeon.commerce.security.token.PostAuthorizationToken;
import dev.smjeon.commerce.security.token.PreAuthorizationToken;
import dev.smjeon.commerce.user.application.UserInternalService;
import dev.smjeon.commerce.user.domain.Email;
import dev.smjeon.commerce.user.domain.Password;
import dev.smjeon.commerce.user.domain.User;
import dev.smjeon.commerce.user.exception.NotFoundUserException;
import dev.smjeon.commerce.user.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Component
public class FormLoginAuthenticationProvider implements AuthenticationProvider {

    private static final String INACTIVE_USER_EXCEPTION_MESSAGE = "비활성화 된 사용자입니다.";
    private static final String LOCKED_USER_EXCEPTION_MESSAGE = "잠긴 계정입니다. 잠금해제 해주세요.";

    private UserInternalService userService;
    private UserRepository userRepository;

    public FormLoginAuthenticationProvider(UserInternalService userService, UserRepository userRepository) {
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

        checkUserStatus(user);

        throw new UnauthorizedException();
    }

    private void checkUserStatus(User user) {
        if (userService.isInactive(user)) {
            throw new DisabledException(INACTIVE_USER_EXCEPTION_MESSAGE);
        }

        if (userService.isDormant(user)) {
            throw new LockedException(LOCKED_USER_EXCEPTION_MESSAGE);
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return PreAuthorizationToken.class.isAssignableFrom(authentication);
    }

}
