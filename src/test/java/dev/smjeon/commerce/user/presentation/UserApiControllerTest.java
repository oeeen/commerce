package dev.smjeon.commerce.user.presentation;

import dev.smjeon.commerce.user.domain.Email;
import dev.smjeon.commerce.user.domain.NickName;
import dev.smjeon.commerce.user.domain.Password;
import dev.smjeon.commerce.user.dto.UserRequestDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;

@AutoConfigureWebTestClient
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserApiControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    void show() {
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
    void signUp() {
        String userName = "전성모";
        Email email = new Email("oeeen3@gmail.com");
        NickName nickName = new NickName("Martin");
        Password password = new Password("aA12345!");
        UserRequestDto userRequestDto = new UserRequestDto(email, userName, nickName, password);

        webTestClient.post()
                .uri("/api/users/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(userRequestDto), UserRequestDto.class)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody()
                .jsonPath("$.email.email").isEqualTo(email.getEmail())
                .jsonPath("$.name").isEqualTo(userName)
                .jsonPath("$.nickName.nickName").isEqualTo(nickName.getNickName());
    }
}
