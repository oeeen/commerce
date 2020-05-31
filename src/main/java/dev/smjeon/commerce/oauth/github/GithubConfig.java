package dev.smjeon.commerce.oauth.github;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Configuration
@ConfigurationProperties(prefix = "naver.client")
public class GithubConfig {
    private String clientId;
    private String clientSecret;
}
