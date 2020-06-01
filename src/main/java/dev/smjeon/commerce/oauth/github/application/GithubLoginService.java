package dev.smjeon.commerce.oauth.github.application;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import dev.smjeon.commerce.oauth.common.application.SocialLoginService;
import dev.smjeon.commerce.oauth.common.dto.SocialUserInfo;
import dev.smjeon.commerce.oauth.github.GithubConfig;
import dev.smjeon.commerce.oauth.github.dto.GithubToken;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class GithubLoginService implements SocialLoginService {
    private GithubConfig githubConfig;

    public GithubLoginService(GithubConfig githubConfig) {
        this.githubConfig = githubConfig;
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
        WebClient webClient = WebClient.builder()
                .baseUrl(githubConfig.getAccessTokenUrl())
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .build();

        String body = webClient.post()
                .body(BodyInserters.fromFormData("client_id", githubConfig.getClientId())
                        .with("client_secret", githubConfig.getClientSecret())
                        .with("code", code))
                .retrieve()
                .bodyToMono(String.class)
                .block();

        GithubToken token = new Gson().fromJson(body, GithubToken.class);

        return token.getAccessToken();
    }

    @Override
    public SocialUserInfo getUserInfo(String accessToken) {
        WebClient webClient = WebClient.builder()
                .baseUrl(githubConfig.getUserInfoUrl())
                .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .defaultHeader(HttpHeaders.AUTHORIZATION, "token " + accessToken)
                .build();

        String body = webClient.get()
                .retrieve()
                .bodyToMono(String.class)
                .block();

        JsonObject jsonObject = new JsonParser().parse(body).getAsJsonObject();
        String id = jsonObject.get("id").getAsString();
        String nickName = jsonObject.get("name").getAsString();
        String email = jsonObject.get("email").getAsString();

        SocialUserInfo userInfo = new SocialUserInfo(id, nickName, email);
        return userInfo;
    }
}
