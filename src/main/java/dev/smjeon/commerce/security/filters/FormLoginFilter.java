package dev.smjeon.commerce.security.filters;

import dev.smjeon.commerce.security.token.PreAuthorizationToken;
import dev.smjeon.commerce.user.domain.Email;
import dev.smjeon.commerce.user.domain.Password;
import dev.smjeon.commerce.user.dto.UserLoginRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class FormLoginFilter extends AbstractAuthenticationProcessingFilter {
    private static final Logger logger = LoggerFactory.getLogger(FormLoginFilter.class);

    private AuthenticationSuccessHandler authenticationSuccessHandler;
    private AuthenticationFailureHandler authenticationFailureHandler;

    public FormLoginFilter(String defaultUrl,
                           AuthenticationSuccessHandler successHandler,
                           AuthenticationFailureHandler failureHandler) {
        super(defaultUrl);
        this.authenticationSuccessHandler = successHandler;
        this.authenticationFailureHandler = failureHandler;
    }

    protected FormLoginFilter(String defaultFilterProcessesUrl) {
        super(defaultFilterProcessesUrl);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res) throws AuthenticationException, IOException, ServletException {
        String email = req.getParameter("email");
        String password = req.getParameter("password");

        UserLoginRequest userLoginRequest = new UserLoginRequest(new Email(email), new Password(password));
        PreAuthorizationToken token = new PreAuthorizationToken(userLoginRequest);

        logger.debug("Requested Login Email: {}", email);

        return super.getAuthenticationManager().authenticate(token);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest req, HttpServletResponse res, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        this.authenticationSuccessHandler.onAuthenticationSuccess(req, res, authResult);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest req, HttpServletResponse res, AuthenticationException failed) throws IOException, ServletException {
        this.authenticationFailureHandler.onAuthenticationFailure(req, res, failed);
    }
}
