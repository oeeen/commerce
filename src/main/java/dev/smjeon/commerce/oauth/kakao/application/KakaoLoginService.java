package dev.smjeon.commerce.oauth.kakao.application;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import dev.smjeon.commerce.oauth.common.application.SocialLoginService;
import dev.smjeon.commerce.oauth.common.dto.SocialUserInfo;
import dev.smjeon.commerce.oauth.kakao.KakaoConfig;
import dev.smjeon.commerce.oauth.kakao.dto.KakaoToken;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class KakaoLoginService implements SocialLoginService {
    private KakaoConfig kakaoConfig;

    public KakaoLoginService(KakaoConfig kakaoConfig) {
        this.kakaoConfig = kakaoConfig;
    }

    @Override
    public String getRedirectUrl() {
        StringBuilder redirectUrl = new StringBuilder();
        redirectUrl.append(kakaoConfig.getAuthorizationUrl());
        redirectUrl.append("?client_id=").append(kakaoConfig.getClientId());
        redirectUrl.append("&redirect_uri=").append(kakaoConfig.getRedirectUrl());
        redirectUrl.append("&response_type=code");

        return redirectUrl.toString();
    }

    @Override
    public String getAccessToken(String code) {
        WebClient webClient = WebClient.builder()
                .baseUrl(kakaoConfig.getAccessTokenUrl())
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .build();

        String body = webClient.post()
                .body(BodyInserters.fromFormData("grant_type", "authorization_code")
                        .with("client_id", kakaoConfig.getClientId())
                        .with("redirect_uri", kakaoConfig.getRedirectUrl())
                        .with("client_secret", kakaoConfig.getClientSecret())
                        .with("code", code))
                .retrieve()
                .bodyToMono(String.class)
                .block();

        KakaoToken token = new Gson().fromJson(body, KakaoToken.class);

        return token.getAccessToken();
    }

    @Override
    public SocialUserInfo getUserInfo(String accessToken) {
        WebClient webClient = WebClient.builder()
                .baseUrl(kakaoConfig.getUserInfoUrl())
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                .build();

        String body = webClient.post()
                .retrieve()
                .bodyToMono(String.class)
                .block();

        String kakaoId = new JsonParser().parse(body).getAsJsonObject().get("id").getAsString();
        JsonObject properties = new JsonParser().parse(body).getAsJsonObject().get("properties").getAsJsonObject();
        String nickName = properties.getAsJsonObject().get("nickname").getAsString();
        JsonObject kakaoAccount = new JsonParser().parse(body).getAsJsonObject().get("kakao_account").getAsJsonObject();
        String email = kakaoAccount.getAsJsonObject().get("email").getAsString();

        SocialUserInfo userInfo = new SocialUserInfo(kakaoId, nickName, email);
        return userInfo;
    }
}
