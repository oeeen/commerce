package dev.smjeon.commerce.security.handlers;

import dev.smjeon.commerce.security.UserContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static dev.smjeon.commerce.support.LoginInterceptor.LOGIN_SESSION_ATTRIBUTE;

@Component
public class FormLoginAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    private static final Logger logger = LoggerFactory.getLogger(FormLoginAuthenticationSuccessHandler.class);

    @Override
    public void onAuthenticationSuccess(HttpServletRequest req, HttpServletResponse res, Authentication auth)
            throws IOException, ServletException {
        HttpSession httpSession = req.getSession();
        UserContext userContext = (UserContext) auth.getPrincipal();
        httpSession.setAttribute(LOGIN_SESSION_ATTRIBUTE, userContext);

        logger.debug("Authentication Success - UserName: {}", userContext.getName());
        res.sendRedirect("/");
    }
}
