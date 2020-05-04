package dev.smjeon.commerce.user.presentation;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

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
}
