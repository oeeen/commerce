package dev.smjeon.commerce.oauth.application;

import dev.smjeon.commerce.oauth.SocialUserInfo;
import dev.smjeon.commerce.oauth.domain.SocialUserRepository;
import dev.smjeon.commerce.oauth.github.GithubConfig;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class GithubLoginService implements SocialLoginService {
    private GithubConfig githubConfig;
    private SocialUserRepository socialUserRepository;
    private ModelMapper modelMapper;

    public GithubLoginService(GithubConfig githubConfig, SocialUserRepository socialUserRepository, ModelMapper modelMapper) {
        this.githubConfig = githubConfig;
        this.socialUserRepository = socialUserRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public String getRedirectUrl() {
        StringBuilder redirectUrl = new StringBuilder();
        redirectUrl.append(githubConfig.getAuthorizationUrl());
        redirectUrl.append("?client_id=").append(githubConfig.getClientId());
        redirectUrl.append("&redirect_uri=").append(githubConfig.getRedirectUrl());

        return redirectUrl.toString();
    }

    @Override
    public String getAccessToken(String code) {
        return null;
    }

    @Override
    public SocialUserInfo getUserInfo(String accessToken) {
        return null;
    }
}
