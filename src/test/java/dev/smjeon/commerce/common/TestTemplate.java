package dev.smjeon.commerce.common;

import dev.smjeon.commerce.user.domain.Email;
import dev.smjeon.commerce.user.domain.NickName;
import dev.smjeon.commerce.user.domain.Password;
import dev.smjeon.commerce.user.dto.UserLoginRequest;
import dev.smjeon.commerce.user.dto.UserSignUpRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import reactor.core.publisher.Mono;

import java.util.Objects;

@AutoConfigureWebTestClient
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        properties = {"spring.config.location=classpath:oauth.yml",
                "spring.config.location=classpath:application.yml"})
public class TestTemplate {

    @Autowired
    protected WebTestClient webTestClient;

    protected UserSignUpRequest userSignUpRequest = new UserSignUpRequest(
            new Email("test@gmail.com"),
            "Seongmo",
            new NickName("Martin"),
            new Password("Aa12345!")
    );

    protected UserLoginRequest userLoginRequest = new UserLoginRequest(
            new Email("test@gmail.com"),
            new Password("Aa12345!")
    );

    protected void register(UserSignUpRequest userSignUpRequest) {
        webTestClient.post()
                .uri("/api/users/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(userSignUpRequest), UserSignUpRequest.class)
                .exchange()
                .expectStatus()
                .isOk();
    }

    protected WebTestClient.ResponseSpec request(HttpMethod method, String uri, Object object, HttpStatus httpStatus) {
        return webTestClient.method(method).uri(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(object), Object.class)
                .exchange()
                .expectStatus()
                .isEqualTo(httpStatus);
    }

    protected WebTestClient.ResponseSpec loginAndRequest(HttpMethod method, String url, Object object, HttpStatus httpStatus, String sessionId) {
        return webTestClient.method(method).uri(url)
                .cookie("JSESSIONID", sessionId)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(object), Object.class)
                .exchange()
                .expectStatus()
                .isEqualTo(httpStatus);
    }

    protected WebTestClient.BodyContentSpec respondApi(WebTestClient.ResponseSpec responseSpec) {
        return responseSpec
                .expectHeader()
                .contentType(MediaType.APPLICATION_JSON)
                .expectBody();
    }

    protected String loginSessionId(Email email, Password password) {
        return Objects.requireNonNull(webTestClient.method(HttpMethod.POST).uri("/api/users/signin")
                .body(BodyInserters.fromFormData("email", email.getEmail())
                        .with("password", password.getValue()))
                .exchange()
                .returnResult(String.class)
                .getResponseCookies()
                .getFirst("JSESSIONID"))
                .getValue();
    }
}
