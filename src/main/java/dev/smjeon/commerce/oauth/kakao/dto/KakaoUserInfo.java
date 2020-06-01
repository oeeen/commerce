package dev.smjeon.commerce.oauth.kakao.dto;

import lombok.Getter;

@Getter
public class KakaoUserInfo {
    private String oauthId;
    private String nickName;
    private String email;

    public KakaoUserInfo(String oauthId, String nickName, String email) {
        this.oauthId = oauthId;
        this.nickName = nickName;
        this.email = email;
    }
}
