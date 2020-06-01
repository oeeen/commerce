package dev.smjeon.commerce.security.token;

public class KakaoPreAuthorizationToken extends SocialPreAuthorizationToken {
    public KakaoPreAuthorizationToken(Object principal, Object credentials) {
        super(principal, credentials);
    }
}
