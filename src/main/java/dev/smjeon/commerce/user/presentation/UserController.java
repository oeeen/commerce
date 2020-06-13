package dev.smjeon.commerce.user.presentation;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class UserController {

    @GetMapping("/login")
    public ModelAndView showLoginPage(@RequestParam(value = "error", required = false) String error,
                                      @RequestParam(value = "exception", required = false) String exception,
                                      ModelAndView mav) {
        mav.addObject("error", error);
        mav.addObject("exception", exception);
        mav.setViewName("login");

        return mav;
    }

    @GetMapping("/signup")
    public String showSignUpPage() {
        return "signup";
    }

    @GetMapping("/denied")
    public ModelAndView accessDenied(@RequestParam(value = "exception", required = false) String exception,
                                     ModelAndView mav) {
        mav.addObject("exception", exception);
        mav.setViewName("/denied");

        return mav;
    }
}
