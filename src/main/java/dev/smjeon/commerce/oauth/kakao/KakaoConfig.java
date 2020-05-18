package dev.smjeon.commerce.oauth.kakao;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Configuration
@ConfigurationProperties(prefix = "kakao.client")
public class KakaoConfig {
    private String clientId;
    private String clientSecret;
    private String authorizationUrl;
    private String accessTokenUrl;
    private String redirectUrl;
    private String userInfoUrl;
    private String logOutUrl;
}
