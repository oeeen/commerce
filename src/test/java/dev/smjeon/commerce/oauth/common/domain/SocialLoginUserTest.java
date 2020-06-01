package dev.smjeon.commerce.oauth.common.domain;

import dev.smjeon.commerce.user.domain.Email;
import dev.smjeon.commerce.user.domain.NickName;
import dev.smjeon.commerce.user.domain.UserRole;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SocialLoginUserTest {

    @Test
    @DisplayName("UserRole이 업데이트 됩니다.")
    void updateUserRole() {
        SocialLoginUser user = new SocialLoginUser("123", new Email("oeeen3@gmail.com"), new NickName("Martin"), UserRole.BUYER);
        assertEquals(user.getUserRole(), UserRole.BUYER);

        user.updateUserRole(UserRole.ADMIN);
        assertEquals(user.getUserRole(), UserRole.ADMIN);
    }
}