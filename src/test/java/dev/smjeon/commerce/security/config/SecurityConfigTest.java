package dev.smjeon.commerce.security.config;

import dev.smjeon.commerce.CommerceApplication;
import dev.smjeon.commerce.user.domain.UserRole;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.h2.H2ConsoleProperties;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.logout;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.request;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {CommerceApplication.class, H2ConsoleProperties.class})
@WebAppConfiguration
class SecurityConfigTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mvc;

    @BeforeEach
    void setUp() {
        mvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .apply(springSecurity())
                .build();
    }

    @Test
    @DisplayName("Admin 권한으로 로그인")
    void loginWithAdmin() throws Exception {
        mvc
                .perform(
                        formLogin("/api/users/signin")
                                .user("email", "admin@gmail.com")
                                .password("password", "Aa12345!"))
                .andExpect(authenticated().withRoles(UserRole.ADMIN.name()));
    }

    @Test
    @DisplayName("로그아웃 요청")
    void requestLogout() throws Exception {
        mvc
                .perform(logout("/api/users/logout"))
                .andExpect(status().isOk())
                .andExpect(request().sessionAttributeDoesNotExist("JSESSIONID"));
    }

    @Test
    @DisplayName("잘못된 경로로 로그아웃 요청 시 해당 경로에 POST method not allow (405)")
    void requestWrongPath() throws Exception {
        mvc
                .perform(logout("/api/users/signout"))
                .andExpect(status().is4xxClientError());
    }
}