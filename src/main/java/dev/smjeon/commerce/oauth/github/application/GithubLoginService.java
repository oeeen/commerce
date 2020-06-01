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
    private static final String PREFIX = "token ";
    private static final String NAME = "name";

    private GithubConfig githubConfig;

    public GithubLoginService(GithubConfig githubConfig) {
        this.githubConfig = githubConfig;
    }

    @Override
    public String getRedirectUrl() {
        return githubConfig.getAuthorizationUrl() +
                "?" + CLIENT_ID + EQUAL + githubConfig.getClientId() +
                "&" + REDIRECT_URI + EQUAL + githubConfig.getRedirectUrl();
    }

    @Override
    public String getAccessToken(String code) {
        WebClient webClient = WebClient.builder()
                .baseUrl(githubConfig.getAccessTokenUrl())
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .build();

        String body = webClient.post()
                .body(BodyInserters.fromFormData(CLIENT_ID, githubConfig.getClientId())
                        .with(CLIENT_SECRET, githubConfig.getClientSecret())
                        .with(CODE, code))
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
                .defaultHeader(HttpHeaders.AUTHORIZATION, PREFIX + accessToken)
                .build();

        String body = webClient.get()
                .retrieve()
                .bodyToMono(String.class)
                .block();

        JsonObject jsonObject = new JsonParser().parse(body).getAsJsonObject();

        String id = jsonObject.get(ID).getAsString();
        String nickName = jsonObject.get(NAME).getAsString();
        String email = jsonObject.get(EMAIL).getAsString();

        return new SocialUserInfo(id, nickName, email);
    }
}
