package dev.smjeon.commerce.oauth.application;

import dev.smjeon.commerce.oauth.SocialProviders;
import dev.smjeon.commerce.oauth.kakao.KakaoConfig;
import org.springframework.stereotype.Service;

@Service
public class SocialLoginService {
    private KakaoConfig kakaoConfig;

    public SocialLoginService(KakaoConfig kakaoConfig) {
        this.kakaoConfig = kakaoConfig;
    }

    public String getRedirectUrl(SocialProviders socialProvider) {
        StringBuilder redirectUrl = new StringBuilder();
        if (socialProvider.equals(SocialProviders.KAKAO)) {
            redirectUrl.append(kakaoConfig.getAuthorizationUrl());
            redirectUrl.append("?client_id=").append(kakaoConfig.getClientId());
            redirectUrl.append("&redirect_uri=").append(kakaoConfig.getRedirectUrl());
            redirectUrl.append("&response_type=code");
        }
        return redirectUrl.toString();
    }
}
