package dev.smjeon.commerce.user.presentation;

import dev.smjeon.commerce.common.TestTemplate;
import dev.smjeon.commerce.user.domain.Email;
import dev.smjeon.commerce.user.domain.NickName;
import dev.smjeon.commerce.user.domain.Password;
import dev.smjeon.commerce.user.dto.UserSignUpRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.BodyInserters;

import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;

public class UserApiControllerTest extends TestTemplate {

    @Test
    @DisplayName("유저 리스트가 출력됩니다.")
    void showUsers() {
        register(userSignUpRequest);

        respondApi(loginAndRequest(HttpMethod.GET, "/api/users", Void.class, HttpStatus.OK,
                loginSessionId(userLoginRequest.getEmail(), userLoginRequest.getPassword())))
                .consumeWith(res -> {
                    String body = new String(Objects.requireNonNull(res.getResponseBody()));
                    System.out.println(body);
                    assertThat(body.contains("Seongmo"));
                });
    }


    @Test
    @DisplayName("회원 가입이 가능합니다.")
    void signUp() {
        String userName = "전성모";
        Email email = new Email("oeeen3@gmail.com");
        NickName nickName = new NickName("Martin");
        Password password = new Password("aA12345!");
        UserSignUpRequest userSignUpRequest = new UserSignUpRequest(email, userName, nickName, password);

        respondApi(request(HttpMethod.POST, "/api/users/signup", userSignUpRequest, HttpStatus.OK))
                .jsonPath("$.email.email").isEqualTo(email.getEmail())
                .jsonPath("$.name").isEqualTo(userName)
                .jsonPath("$.nickName.nickName").isEqualTo(nickName.getNickName());
    }

    @Test
    @DisplayName("로그인이 가능합니다.")
    void signIn() {
        String userName = "테스트";
        Email email = new Email("oeeen@naver.com");
        NickName nickName = new NickName("Seongmo");
        Password password = new Password("aA12345!");
        UserSignUpRequest userSignUpRequest = new UserSignUpRequest(email, userName, nickName, password);
        register(userSignUpRequest);

        webTestClient.post()
                .uri("/api/users/signin")
                .body(BodyInserters.fromFormData("email", email.getEmail())
                        .with("password", password.getValue()))
                .exchange()
                .expectStatus()
                .isFound();
    }
}
