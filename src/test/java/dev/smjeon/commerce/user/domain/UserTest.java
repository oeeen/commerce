package dev.smjeon.commerce.user.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserTest {
    @Test
    @DisplayName("유저를 새로 생성할 때는 이메일, 비밀번호, 실명, 닉네임이 필요합니다.")
    void constructUser() {
        String name = "Seongmo";
        String nickName = "Martin";
        Email email = new Email("oeeen3@gmail.com");
        String password = "Aa12345!";

        assertDoesNotThrow(() -> new User(email, password, name, nickName));
    }

    @Test
    @DisplayName("추가 정보로 성별, 연령정보, 생일 정보를 받으면 기존 유저를 업데이트합니다.")
    void moreInformation() {
        String gender = "남";
        int age = 20;
        LocalDate birthday = LocalDate.of(1992, 6, 16);

        String name = "Seongmo";
        String nickName = "Martin";
        Email email = new Email("oeeen3@gmail.com");
        String password = "Aa12345!";

        User user = new User(email, password, name, nickName);
        user.updateGender(gender);
        user.updateAge(age);
        user.updateBirthday(birthday);

        assertEquals(user.getGender(), gender);
        assertEquals(user.getAge(), age);
        assertEquals(user.getBirthday(), birthday);
    }
}
