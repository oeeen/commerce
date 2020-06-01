package dev.smjeon.commerce.oauth.presentation;

import dev.smjeon.commerce.oauth.SocialProviders;
import dev.smjeon.commerce.oauth.application.SocialLoginService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class SocialLoginController {
    private final SocialLoginService socialLoginService;

    public SocialLoginController(SocialLoginService socialLoginService) {
        this.socialLoginService = socialLoginService;
    }

    @GetMapping("/login/{socialProvider}")
    public RedirectView loginWithSocial(@PathVariable SocialProviders socialProvider) {
        String redirectUrl = socialLoginService.getRedirectUrl(socialProvider);
        return new RedirectView(redirectUrl);
    }
}
