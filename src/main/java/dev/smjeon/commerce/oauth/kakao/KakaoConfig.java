package dev.smjeon.commerce.oauth.kakao;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "kakao.client")
@Getter
@Setter
public class KakaoConfig {
    private String clientId;
    private String clientSecret;
    private String authorizationUrl;
    private String accessTokenUrl;
    private String redirectUrl;
    private String userInfoUrl;
    private String logOutUrl;
}
