package dev.smjeon.commerce.oauth.common.application;

import dev.smjeon.commerce.oauth.common.dto.SocialUserInfo;

public interface SocialLoginService {
    String getRedirectUrl();

    String getAccessToken(String code);

    SocialUserInfo getUserInfo(String accessToken);
}
