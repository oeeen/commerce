package dev.smjeon.commerce.security.providers;

import dev.smjeon.commerce.oauth.application.SocialLoginService;
import dev.smjeon.commerce.oauth.domain.SocialUserRepository;
import dev.smjeon.commerce.security.token.GithubPreAuthorizationToken;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class GithubLoginAuthenticationProvider extends SocialLoginAuthenticationProvider {

    public GithubLoginAuthenticationProvider(SocialLoginService githubLoginService, SocialUserRepository socialUserRepository, ModelMapper modelMapper) {
        super(githubLoginService, socialUserRepository, modelMapper);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return GithubPreAuthorizationToken.class.isAssignableFrom(authentication);
    }
}