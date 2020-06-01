package dev.smjeon.commerce.oauth.kakao.application;

import dev.smjeon.commerce.oauth.kakao.KakaoConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;

@ExtendWith(SpringExtension.class)
class KakaoLoginServiceTest {

    @InjectMocks
    private KakaoLoginService kakaoLoginService;

    @Mock
    private KakaoConfig kakaoConfig;

    @Test
    @DisplayName("kakao의 redirectUrl 이 나옵니다.")
    void getRedirectUrl() {
        String authorizationUrl = "https://kakao.redirect/authorize";
        String clientId = "testId";
        String redirectUrl = "https://redirect.da";

        given(kakaoConfig.getAuthorizationUrl()).willReturn(authorizationUrl);
        given(kakaoConfig.getClientId()).willReturn(clientId);
        given(kakaoConfig.getRedirectUrl()).willReturn(redirectUrl);

        assertEquals(kakaoLoginService.getRedirectUrl(),
                authorizationUrl + "?client_id=" + clientId +
                        "&redirect_uri=" + redirectUrl +
                        "&response_type=code");
    }
}