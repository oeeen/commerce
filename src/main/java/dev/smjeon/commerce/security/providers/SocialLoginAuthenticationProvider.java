package dev.smjeon.commerce.security.providers;

import dev.smjeon.commerce.oauth.application.SocialLoginService;
import dev.smjeon.commerce.oauth.domain.SocialLoginUser;
import dev.smjeon.commerce.oauth.domain.SocialUserRepository;
import dev.smjeon.commerce.oauth.kakao.dto.KakaoUserInfo;
import dev.smjeon.commerce.security.UserContext;
import dev.smjeon.commerce.security.token.SocialPostAuthorizationToken;
import dev.smjeon.commerce.security.token.SocialPreAuthorizationToken;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Component
public class SocialLoginAuthenticationProvider implements AuthenticationProvider {
    private final SocialLoginService socialLoginService;
    private final SocialUserRepository socialUserRepository;

    public SocialLoginAuthenticationProvider(SocialLoginService socialLoginService, SocialUserRepository socialUserRepository) {
        this.socialLoginService = socialLoginService;
        this.socialUserRepository = socialUserRepository;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        SocialPreAuthorizationToken token = (SocialPreAuthorizationToken) authentication;
        String code = token.getCode();
        String accessToken = socialLoginService.getAccessToken(code);
        KakaoUserInfo userInfo = socialLoginService.getUserInfo(accessToken);

        SocialLoginUser user = socialUserRepository.findByOauthId(userInfo.getOauthId())
                .orElseGet(() -> socialLoginService.save(userInfo));

        UserContext userContext = new UserContext(
                user.getId(),
                user.getOauthId(),
                user.getNickName().getNickName(),
                user.getUserRole());

        return new SocialPostAuthorizationToken(userContext);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return SocialPreAuthorizationToken.class.isAssignableFrom(authentication);
    }
}
