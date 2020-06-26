package dev.smjeon.commerce.user.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UserTest {
    private String name = "Seongmo";
    private NickName nickName = new NickName("Martin");
    private Email email = new Email("oeeen3@gmail.com");
    private Password password = new Password("Aa12345!");

    @Test
    @DisplayName("유저를 새로 생성할 때는 이메일, 비밀번호, 실명, 닉네임이 필요합니다.")
    void constructUser() {
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

    @Test
    @DisplayName("현재 계정 상태를 Active 에서 deactivate 가능합니다.")
    void isActive() {
        User user = new User(email, password, name, nickName, UserRole.BUYER, UserStatus.ACTIVE);
        assertTrue(user.isActive());

        user.deactivate();

        assertFalse(user.isActive());
    }

    @Test
    @DisplayName("현재 계정 상태를 Inactive 에서 activate 가능합니다.")
    void deactivateUser() {
        User user = new User(email, password, name, nickName, UserRole.BUYER, UserStatus.INACTIVE);
        assertFalse(user.isActive());

        user.activate();

        assertTrue(user.isActive());
    }

    @Test
    @DisplayName("현재 계정이 권한에 따라 ADMIN = true, OTHER = false")
    void isAdmin() {
        User admin = new User(email, password, name, nickName, UserRole.ADMIN, UserStatus.ACTIVE);
        assertTrue(admin.isAdmin());

        User other = new User(email, password, name, nickName, UserRole.SELLER, UserStatus.ACTIVE);
        assertFalse(other.isAdmin());
    }

    @Test
    @DisplayName("로그인 시 lastLogin field가 현재 시간으로 업데이트 됩니다.")
    void login() {
        User user = new User(email, password, name, nickName, UserRole.SELLER, UserStatus.ACTIVE);

        user.signUpOrLogin();
        assertEquals(user.getLastLogin(), LocalDate.now());
    }

    @Test
    @DisplayName("현재 계정 상태를 INACTIVE, DORMANT인지 확인합니다.")
    void userState() {
        User inactiveUser = new User(email, password, name, nickName, UserRole.ADMIN, UserStatus.INACTIVE);

        assertTrue(inactiveUser.isInactive());

        User dormantUser = new User(email, password, name, nickName, UserRole.ADMIN, UserStatus.DORMANT);

        assertTrue(dormantUser.isDormant());
    }
}
