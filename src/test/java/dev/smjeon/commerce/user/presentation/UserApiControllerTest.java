package dev.smjeon.commerce.user.presentation;

import dev.smjeon.commerce.user.domain.Email;
import dev.smjeon.commerce.user.domain.NickName;
import dev.smjeon.commerce.user.domain.Password;
import dev.smjeon.commerce.user.dto.UserLoginRequest;
import dev.smjeon.commerce.user.dto.UserSignUpRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import reactor.core.publisher.Mono;

import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;

@AutoConfigureWebTestClient
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserApiControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    @DisplayName("유저 리스트가 출력됩니다.")
    void showUsers() {
        webTestClient.get()
                .uri("/api/users")
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody()
                .consumeWith(res -> {
                    String body = new String(Objects.requireNonNull(res.getResponseBody()));
                    System.out.println(body);
                    assertThat(body.contains("smjeon"));
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

        webTestClient.post()
                .uri("/api/users/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(userSignUpRequest), UserSignUpRequest.class)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody()
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

        UserLoginRequest userLoginRequest = new UserLoginRequest(email, password);
        webTestClient.post()
                .uri("/api/users/signin")
                .body(BodyInserters.fromFormData("email", email.getEmail())
                        .with("password", password.getValue()))
                .exchange()
                .expectStatus()
                .isFound();
    }

    private void register(UserSignUpRequest userSignUpRequest) {
        webTestClient.post()
                .uri("/api/users/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(userSignUpRequest), UserSignUpRequest.class)
                .exchange()
                .expectStatus()
                .isOk();
    }
}
