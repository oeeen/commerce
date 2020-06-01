package dev.smjeon.commerce.security.token;

public class GithubPreAuthorizationToken extends SocialPreAuthorizationToken {
    public GithubPreAuthorizationToken(Object principal, Object credentials) {
        super(principal, credentials);
    }
}
