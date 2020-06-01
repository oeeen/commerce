package dev.smjeon.commerce.security.providers;

import dev.smjeon.commerce.oauth.SocialUserInfo;
import dev.smjeon.commerce.oauth.application.SocialLoginService;
import dev.smjeon.commerce.oauth.domain.SocialLoginUser;
import dev.smjeon.commerce.oauth.domain.SocialUserRepository;
import dev.smjeon.commerce.security.UserContext;
import dev.smjeon.commerce.security.token.SocialPostAuthorizationToken;
import dev.smjeon.commerce.security.token.SocialPreAuthorizationToken;
import dev.smjeon.commerce.user.domain.UserRole;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

public abstract class SocialLoginAuthenticationProvider implements AuthenticationProvider {
    private final SocialLoginService socialLoginService;
    private final SocialUserRepository socialUserRepository;
    private final ModelMapper modelMapper;

    public SocialLoginAuthenticationProvider(SocialLoginService socialLoginService, SocialUserRepository socialUserRepository, ModelMapper modelMapper) {
        this.socialLoginService = socialLoginService;
        this.socialUserRepository = socialUserRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        SocialPreAuthorizationToken token = (SocialPreAuthorizationToken) authentication;
        String code = token.getCode();
        String accessToken = socialLoginService.getAccessToken(code);
        SocialUserInfo userInfo = socialLoginService.getUserInfo(accessToken);

        SocialLoginUser user = socialUserRepository.findByOauthId(userInfo.getOauthId())
                .orElseGet(() -> save(userInfo));

        UserContext userContext = new UserContext(
                user.getId(),
                user.getOauthId(),
                user.getNickName().getNickName(),
                user.getUserRole());

        return new SocialPostAuthorizationToken(userContext);
    }

    private SocialLoginUser save(SocialUserInfo userInfo) {
        SocialLoginUser user = modelMapper.map(userInfo, SocialLoginUser.class);
        user.updateUserRole(UserRole.BUYER);

        return socialUserRepository.save(user);
    }
}
