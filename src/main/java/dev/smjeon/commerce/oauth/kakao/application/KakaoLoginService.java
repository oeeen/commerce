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
    private static final String PREFIX = "Bearer ";
    private static final String GRANT_TYPE = "grant_type";
    private static final String AUTHORIZATION_CODE = "authorization_code";
    private static final String NICKNAME = "nickname";

    private KakaoConfig kakaoConfig;

    public KakaoLoginService(KakaoConfig kakaoConfig) {
        this.kakaoConfig = kakaoConfig;
    }

    @Override
    public String getRedirectUrl() {
        return kakaoConfig.getAuthorizationUrl() +
                PARAM_SEPARATOR + CLIENT_ID + EQUAL + kakaoConfig.getClientId() +
                AND + REDIRECT_URI + EQUAL + kakaoConfig.getRedirectUrl() +
                AND + RESPONSE_TYPE + EQUAL + CODE;
    }

    @Override
    public String getAccessToken(String code) {
        WebClient webClient = WebClient.builder()
                .baseUrl(kakaoConfig.getAccessTokenUrl())
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .build();

        String body = webClient.post()
                .body(BodyInserters.fromFormData(GRANT_TYPE, AUTHORIZATION_CODE)
                        .with(CLIENT_ID, kakaoConfig.getClientId())
                        .with(REDIRECT_URI, kakaoConfig.getRedirectUrl())
                        .with(CLIENT_SECRET, kakaoConfig.getClientSecret())
                        .with(CODE, code))
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
                .defaultHeader(HttpHeaders.AUTHORIZATION, PREFIX + accessToken)
                .build();

        String body = webClient.post()
                .retrieve()
                .bodyToMono(String.class)
                .block();

        JsonObject bodyJsonObject = new JsonParser().parse(body).getAsJsonObject();
        JsonObject properties = bodyJsonObject.getAsJsonObject().get("properties").getAsJsonObject();
        JsonObject kakaoAccount = bodyJsonObject.get("kakao_account").getAsJsonObject();

        String oauthId = bodyJsonObject.get(ID).getAsString();
        String nickName = properties.getAsJsonObject().get(NICKNAME).getAsString();
        String email = kakaoAccount.getAsJsonObject().get(EMAIL).getAsString();

        return new SocialUserInfo(oauthId, nickName, email);
    }
}
