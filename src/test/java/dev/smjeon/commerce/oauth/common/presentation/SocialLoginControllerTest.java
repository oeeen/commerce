package dev.smjeon.commerce.oauth.common.presentation;

import dev.smjeon.commerce.common.TestTemplate;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

class SocialLoginControllerTest extends TestTemplate {
    @Test
    @DisplayName("kakao 로그인 시 해당 url로 redirect됩니다.")
    void kakaoLoginRedirect() {
        request(HttpMethod.GET, "/login/kakao", Void.class, HttpStatus.FOUND)
                .expectHeader()
                .value("Location", Matchers.containsString("/oauth/kakao"));
    }

    @Test
    @DisplayName("github 로그인 시 해당 url로 redirect됩니다.")
    void githubLoginRedirect() {
        request(HttpMethod.GET, "/login/github", Void.class, HttpStatus.FOUND)
                .expectHeader()
                .value("Location", Matchers.containsString("/oauth/github"));
    }
}