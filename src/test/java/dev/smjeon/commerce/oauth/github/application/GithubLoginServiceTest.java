package dev.smjeon.commerce.oauth.github.application;

import dev.smjeon.commerce.oauth.github.GithubConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;

@ExtendWith(SpringExtension.class)
class GithubLoginServiceTest {

    @InjectMocks
    private GithubLoginService githubLoginService;

    @Mock
    private GithubConfig githubConfig;

    @Test
    @DisplayName("github의 redirectUrl 이 나옵니다.")
    void getRedirectUrl() {
        String authorizationUrl = "https://test.com/authorize";
        String clientId = "ABCD";
        String redirectUrl = "https://redirect.da";
        given(githubConfig.getAuthorizationUrl()).willReturn(authorizationUrl);
        given(githubConfig.getClientId()).willReturn(clientId);
        given(githubConfig.getRedirectUrl()).willReturn(redirectUrl);

        assertEquals(githubLoginService.getRedirectUrl(),
                authorizationUrl + "?client_id=" + clientId
                        + "&redirect_uri=" + redirectUrl);
    }
}