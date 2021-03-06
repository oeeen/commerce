package dev.smjeon.commerce.security.filters;

import dev.smjeon.commerce.security.token.GithubPreAuthorizationToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class GithubLoginFilter extends SocialLoginFilter {
    private static final Logger logger = LoggerFactory.getLogger(GithubLoginFilter.class);

    public GithubLoginFilter(String defaultFilterProcessesUrl) {
        super(defaultFilterProcessesUrl);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        String code = request.getParameter("code");
        logger.debug("Social Authorization code: {}", code);

        return super.getAuthenticationManager().authenticate(new GithubPreAuthorizationToken(code, code));
    }
}
