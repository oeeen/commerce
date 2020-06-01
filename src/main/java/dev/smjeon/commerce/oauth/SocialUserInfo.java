package dev.smjeon.commerce.oauth;

import lombok.Getter;

@Getter
public class SocialUserInfo {
    private String oauthId;
    private String nickName;
    private String email;

    public SocialUserInfo(String oauthId, String nickName, String email) {
        this.oauthId = oauthId;
        this.nickName = nickName;
        this.email = email;
    }
}
