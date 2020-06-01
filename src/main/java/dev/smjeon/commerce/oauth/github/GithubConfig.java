package dev.smjeon.commerce.oauth.github;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "github.client")
@Getter
@Setter
public class GithubConfig {
    private String clientId;
    private String clientSecret;
    private String authorizationUrl;
    private String redirectUrl;
}
