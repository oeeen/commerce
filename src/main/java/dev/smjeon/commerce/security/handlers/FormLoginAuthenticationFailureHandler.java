package dev.smjeon.commerce.security.handlers;

import dev.smjeon.commerce.security.exception.UnauthorizedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class FormLoginAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {
    private static final Logger logger = LoggerFactory.getLogger(FormLoginAuthenticationFailureHandler.class);

    private static final String ERROR_MESSAGE = "Invalid Email or Password";

    @Override
    public void onAuthenticationFailure(HttpServletRequest req, HttpServletResponse res, AuthenticationException exception)
            throws IOException, ServletException {
        String errorMessage = ERROR_MESSAGE;

        if (exception instanceof UnauthorizedException ||
                exception instanceof LockedException ||
                exception instanceof DisabledException) {
            errorMessage = exception.getMessage();
        }

        setDefaultFailureUrl("/login?error=true&exception=" + errorMessage);
        logger.debug("Authentication failure");

        super.onAuthenticationFailure(req, res, exception);
    }
}
