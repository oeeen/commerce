package dev.smjeon.commerce.home;

import dev.smjeon.commerce.common.TestTemplate;
import dev.smjeon.commerce.user.domain.Email;
import dev.smjeon.commerce.user.domain.NickName;
import dev.smjeon.commerce.user.domain.Password;
import dev.smjeon.commerce.user.dto.UserSignUpRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class IndexControllerTest extends TestTemplate {

    @Test
    @DisplayName("로그인 하지 않은 상태로 Index 페이지 접근 시 로그인, 회원가입 버튼이 있습니다.")
    void indexPageWithAnonymous() {
        webTestClient.get()
                .uri("/")
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody()
                .consumeWith(res -> {
                    String body = new String(Objects.requireNonNull(res.getResponseBody()));
                    assertTrue(body.contains("로그인"));
                    assertTrue(body.contains("회원가입"));
                });
    }

    @Test
    @DisplayName("로그인 후 Index 페이지 접근 시 로그아웃 버튼이 있고 로그인, 회원가입 버튼이 없습니다.")
    void indexWithAuthenticated() {
        String userName = "전성모";
        Email email = new Email("test@test.com");
        NickName nickName = new NickName("Martin");
        Password password = new Password("aA12345!");
        UserSignUpRequest userSignUpRequest = new UserSignUpRequest(email, userName, nickName, password);

        request(HttpMethod.POST, "/api/users/signup", userSignUpRequest, HttpStatus.OK);

        loginAndRequest(HttpMethod.GET, "/", Void.class, HttpStatus.OK,
                loginSessionId(userSignUpRequest.getEmail(), userSignUpRequest.getPassword()))
                .expectBody()
                .consumeWith(res -> {
                    String body = new String(Objects.requireNonNull(res.getResponseBody()));
                    System.out.println(body);
                    assertTrue(body.contains("로그아웃"));
                    assertFalse(body.contains("로그인"));
                    assertFalse(body.contains("회원가입"));
                    assertFalse(body.contains("회원조회"));
                });
    }

    @Test
    @DisplayName("관리자로 로그인 후에는 회원조회 버튼이 있습니다.")
    void indexWithAdminAuthority() {
        loginAndRequest(HttpMethod.GET, "/", Void.class, HttpStatus.OK,
                loginSessionId(adminLoginRequest.getEmail(), adminLoginRequest.getPassword()))
                .expectBody()
                .consumeWith(res -> {
                    String body = new String(Objects.requireNonNull(res.getResponseBody()));
                    System.out.println(body);
                    assertTrue(body.contains("로그아웃"));
                    assertFalse(body.contains("로그인"));
                    assertFalse(body.contains("회원가입"));
                    assertTrue(body.contains("회원 조회"));
                });
    }
}
