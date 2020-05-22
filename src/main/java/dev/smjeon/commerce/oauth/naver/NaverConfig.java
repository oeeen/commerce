package dev.smjeon.commerce.oauth.naver;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Configuration
@ConfigurationProperties(prefix = "naver.client")
public class NaverConfig {
    private String clientId;
    private String clientSecret;
}
