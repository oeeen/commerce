package dev.smjeon.commerce.oauth.common.presentation;

import dev.smjeon.commerce.oauth.common.SocialProviders;
import dev.smjeon.commerce.oauth.github.application.GithubLoginService;
import dev.smjeon.commerce.oauth.kakao.application.KakaoLoginService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class SocialLoginController {
    private final KakaoLoginService kakaoLoginService;
    private final GithubLoginService githubLoginService;

    public SocialLoginController(KakaoLoginService kakaoLoginService, GithubLoginService githubLoginService) {
        this.kakaoLoginService = kakaoLoginService;
        this.githubLoginService = githubLoginService;
    }

    @GetMapping("/login/{socialProvider}")
    public RedirectView loginWithSocial(@PathVariable SocialProviders socialProvider) {
        if (SocialProviders.KAKAO.equals(socialProvider)) {
            return new RedirectView(kakaoLoginService.getRedirectUrl());
        }

        return new RedirectView(githubLoginService.getRedirectUrl());
    }
}
