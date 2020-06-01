package dev.smjeon.commerce.security.providers;

import dev.smjeon.commerce.oauth.application.SocialLoginService;
import dev.smjeon.commerce.oauth.domain.SocialUserRepository;
import dev.smjeon.commerce.security.token.KakaoPreAuthorizationToken;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class KakaoLoginAuthenticationProvider extends SocialLoginAuthenticationProvider {

    public KakaoLoginAuthenticationProvider(SocialLoginService kakaoLoginService, SocialUserRepository socialUserRepository, ModelMapper modelMapper) {
        super(kakaoLoginService, socialUserRepository, modelMapper);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return KakaoPreAuthorizationToken.class.isAssignableFrom(authentication);
    }
}
