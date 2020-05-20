package dev.smjeon.commerce.user.presentation;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class UserLoginController {

    @RequestMapping("/login")
    public String showLoginPage() {
        return "login";
    }
}
