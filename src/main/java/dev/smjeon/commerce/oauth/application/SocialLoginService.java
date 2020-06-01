package dev.smjeon.commerce.oauth.application;

import dev.smjeon.commerce.oauth.SocialUserInfo;

public interface SocialLoginService {
    String getRedirectUrl();

    String getAccessToken(String code);

    SocialUserInfo getUserInfo(String accessToken);
}
