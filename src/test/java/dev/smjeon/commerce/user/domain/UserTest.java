package dev.smjeon.commerce.user.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class UserTest {
    @Test
    @DisplayName("유저를 새로 생성할 때는 이메일, 비밀번호, 실명, 닉네임이 필요합니다.")
    void constructUser() {
        String name = "Seongmo";
        String nickName = "Martin";
        String email = "oeeen3@gmail.com";
        String password = "Aa12345!";

        assertDoesNotThrow(() -> new User(email, password, name, nickName));
    }
}
