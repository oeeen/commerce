package dev.smjeon.commerce.security.handlers;

import dev.smjeon.commerce.security.UserContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Component
public class FormLoginAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private static final Logger logger = LoggerFactory.getLogger(FormLoginAuthenticationSuccessHandler.class);
    private static final String LOGIN_SESSION_ATTRIBUTE = "loginUser";

    private RequestCache requestCache = new HttpSessionRequestCache();

    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Override
    public void onAuthenticationSuccess(HttpServletRequest req, HttpServletResponse res, Authentication auth)
            throws IOException, ServletException {
        SecurityContext context = SecurityContextHolder.createEmptyContext();

        context.setAuthentication(auth);
        SecurityContextHolder.setContext(context);

        HttpSession httpSession = req.getSession();
        UserContext userContext = (UserContext) auth.getPrincipal();
        httpSession.setAttribute(LOGIN_SESSION_ATTRIBUTE, userContext);

        logger.debug("Authentication Success - UserName: {}", userContext.getName());

        SavedRequest savedRequest = requestCache.getRequest(req, res);

        setDefaultTargetUrl("/");
        if (savedRequest != null) {
            String redirectUrl = savedRequest.getRedirectUrl();
            setDefaultTargetUrl(redirectUrl);
        }

        redirectStrategy.sendRedirect(req, res, getDefaultTargetUrl());
    }
}
