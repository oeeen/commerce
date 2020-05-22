package dev.smjeon.commerce.oauth.naver;

import dev.smjeon.commerce.oauth.SocialUserProperty;
import dev.smjeon.commerce.user.domain.NickName;

public class NaverUserProperty implements SocialUserProperty {
    private NickName nickName;
    private String profileUrl;

    @Override
    public String getNickName() {
        return this.nickName.getNickName();
    }

    @Override
    public String getProfileUrl() {
        return this.profileUrl;
    }
}
