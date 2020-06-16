package dev.smjeon.commerce.user.presentation;

import dev.smjeon.commerce.common.TestTemplate;
import dev.smjeon.commerce.user.domain.Email;
import dev.smjeon.commerce.user.domain.NickName;
import dev.smjeon.commerce.user.domain.Password;
import dev.smjeon.commerce.user.dto.UserResponse;
import dev.smjeon.commerce.user.dto.UserSignUpRequest;
import org.hamcrest.Matchers;
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
        respondApi(loginAndRequest(HttpMethod.GET, "/api/users", Void.class, HttpStatus.OK,
                loginSessionId(adminLoginRequest.getEmail(), adminLoginRequest.getPassword())))
                .consumeWith(res -> {
                    String body = new String(Objects.requireNonNull(res.getResponseBody()));
                    System.out.println(body);
                    assertThat(body.contains("Seongmo"));
                });
    }

    @Test
    @DisplayName("부족한 권한(일반 사용자)으로 유저 리스트를 요청하면 access denied 페이지로 리다이렉트 됩니다.")
    void showUsersWithInsufficientAuthority() {
        String userName = "일반사용자";
        Email email = new Email("normal@naver.com");
        NickName nickName = new NickName("Normal");
        Password password = new Password("aA12345!");
        UserSignUpRequest userSignUpRequest = new UserSignUpRequest(email, userName, nickName, password);
        register(userSignUpRequest);

        loginAndRequest(HttpMethod.GET, "/api/users", Void.class, HttpStatus.FOUND,
                loginSessionId(userSignUpRequest.getEmail(), userSignUpRequest.getPassword()))
                .expectHeader()
                .value("Location", Matchers.containsString("/denied"));
    }

    @Test
    @DisplayName("권한 없이 유저리스트를 요청하면 로그인 페이지로 리다이렉트 됩니다.")
    void showUsersWithoutAuth() {
        request(HttpMethod.GET, "/api/users", Void.class, HttpStatus.FOUND)
                .expectHeader()
                .value("Location", Matchers.containsString("/login"));
    }

    @Test
    @DisplayName("회원 가입이 가능합니다.")
    void signUp() {
        String userName = "전성모";
        Email email = new Email("oeeen@gmail.com");
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

    @Test
    @DisplayName("회원 탈퇴 요청 시 계정이 Deactive 됩니다.")
    void withdraw() {
        String userName = "회원탈퇴";
        Email email = new Email("withdraw@gmail.com");
        NickName nickName = new NickName("Seongmo");
        Password password = new Password("aA12345!");
        UserSignUpRequest userSignUpRequest = new UserSignUpRequest(email, userName, nickName, password);

        deactivateUser(userSignUpRequest);
    }

    @Test
    @DisplayName("비활성화된 회원은 로그인이 불가능합니다.(인증 실패) 로그인 페이지로 리다이렉트 됩니다.")
    void withdrawWithDeactivatedUser() {
        String userName = "회원탈퇴2";
        Email email = new Email("withdraw2@gmail.com");
        NickName nickName = new NickName("Seongmo");
        Password password = new Password("aA12345!");
        UserSignUpRequest userSignUpRequest = new UserSignUpRequest(email, userName, nickName, password);

        deactivateUser(userSignUpRequest);

        webTestClient.post()
                .uri("/api/users/signin")
                .body(BodyInserters.fromFormData("email", email.getEmail())
                        .with("password", password.getValue()))
                .exchange()
                .expectStatus()
                .isFound()
                .expectHeader()
                .value("Location", Matchers.containsString("/login"));
    }

    private void deactivateUser(UserSignUpRequest userSignUpRequest) {
        UserResponse userResponse = request(HttpMethod.POST, "/api/users/signup", userSignUpRequest, HttpStatus.OK)
                .expectBody(UserResponse.class)
                .returnResult()
                .getResponseBody();

        loginAndRequest(HttpMethod.DELETE, "/api/users/" + userResponse.getId(), Void.class,
                HttpStatus.NO_CONTENT, loginSessionId(userSignUpRequest.getEmail(), userSignUpRequest.getPassword()));
    }
}
