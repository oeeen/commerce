package dev.smjeon.commerce.support;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Component
public class LoginInterceptor extends HandlerInterceptorAdapter {
    public static final String LOGIN_SESSION_ATTRIBUTE = "loginUser";

    @Override
    public boolean preHandle(HttpServletRequest req, HttpServletResponse res, Object handler) throws Exception {
        HttpSession session = req.getSession();
        Object loginSession = session.getAttribute(LOGIN_SESSION_ATTRIBUTE);

        if (loginSession == null) {
            res.sendRedirect("/");

            return false;
        }

        return true;
    }
}
