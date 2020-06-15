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
        NickName nickName = new NickName("Martin");
        Email email = new Email("oeeen3@gmail.com");
        Password password = new Password("Aa12345!");

        assertDoesNotThrow(() -> new User(email, password, name, nickName, UserRole.BUYER, UserStatus.ACTIVE));
    }

    @Test
    @DisplayName("추가 정보로 성별, 연령정보, 생일 정보를 받으면 기존 유저를 업데이트합니다.")
    void moreInformation() {
        Gender gender = Gender.MALE;
        int age = 20;
        LocalDate birthday = LocalDate.of(1992, 6, 16);

        String name = "Seongmo";
        NickName nickName = new NickName("Martin");
        Email email = new Email("oeeen3@gmail.com");
        Password password = new Password("Aa12345!");

        User user = new User(email, password, name, nickName, UserRole.BUYER, UserStatus.ACTIVE);
        user.updateGender(gender);
        user.updateAge(age);
        user.updateBirthday(birthday);

        assertEquals(user.getGender(), gender);
        assertEquals(user.getAge(), age);
        assertEquals(user.getBirthday(), birthday);

        user.updateGender(Gender.FEMALE);
        assertEquals(user.getGender(), Gender.FEMALE);
    }
}
