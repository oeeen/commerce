package dev.smjeon.commerce.oauth.common.application;

import dev.smjeon.commerce.oauth.common.dto.SocialUserInfo;

public interface SocialLoginService {
    String CLIENT_ID = "client_id";
    String CLIENT_SECRET = "client_secret";
    String REDIRECT_URI = "redirect_uri";
    String RESPONSE_TYPE = "response_type";
    String EQUAL = "=";
    String PARAM_SEPARATOR = "?";
    String AND = "&";
    String CODE = "code";
    String ID = "id";
    String EMAIL = "email";

    String getRedirectUrl();

    String getAccessToken(String code);

    SocialUserInfo getUserInfo(String accessToken);
}
