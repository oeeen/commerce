package dev.smjeon.commerce.user.application;

import dev.smjeon.commerce.security.UserContext;
import dev.smjeon.commerce.user.domain.Email;
import dev.smjeon.commerce.user.domain.NickName;
import dev.smjeon.commerce.user.domain.Password;
import dev.smjeon.commerce.user.domain.User;
import dev.smjeon.commerce.user.domain.UserRole;
import dev.smjeon.commerce.user.domain.UserStatus;
import dev.smjeon.commerce.user.dto.UserLoginRequest;
import dev.smjeon.commerce.user.dto.UserSignUpRequest;
import dev.smjeon.commerce.user.exception.DuplicatedEmailException;
import dev.smjeon.commerce.user.exception.InvalidPasswordException;
import dev.smjeon.commerce.user.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
class UserInternalServiceTest {

    @InjectMocks
    private UserInternalService userInternalService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private PasswordEncoder passwordEncoder;

    private String name = "Seongmo";
    private NickName nickName = new NickName("Martin");
    private Email email = new Email("oeeen3@gmail.com");
    private Password password = new Password("Aa12345!");
    private User user = new User(email, password, name, nickName, UserRole.BUYER, UserStatus.ACTIVE);
    private User inactive = new User(email, password, name, nickName, UserRole.BUYER, UserStatus.INACTIVE);
    private User dormant = new User(email, password, name, nickName, UserRole.BUYER, UserStatus.DORMANT);

    @Test
    void findAll() {
        List<User> users = Collections.singletonList(user);
        given(userRepository.findAll()).willReturn(users);

        List<User> response = userInternalService.findAll();
        User userResponse = response.get(0);

        verify(userRepository).findAll();
        assertEquals(userResponse.getEmail(), email);
        assertEquals(userResponse.getNickName(), nickName);
        assertEquals(userResponse.getName(), name);
        assertEquals(userResponse.getUserRole(), UserRole.BUYER);
    }

    @Test
    void findByEmail() {
        given(userRepository.findByEmail(email)).willReturn(Optional.ofNullable(user));
        given(modelMapper.map(user, UserContext.class)).willReturn(mock(UserContext.class));

        userInternalService.findByEmail(email);
        verify(userRepository).findByEmail(email);
    }

    @Test
    @DisplayName("이미 존재하는 이메일일 경우 DuplicatedEmailException이 발생한다.")
    void saveWithDuplicatedEmail() {
        UserSignUpRequest userSignUpRequest = new UserSignUpRequest(email, name, nickName, password);
        given(userRepository.existsByEmail(email)).willReturn(true);
        given(passwordEncoder.encode(anyString())).willReturn(password.getValue());


        assertThrows(DuplicatedEmailException.class, () -> userInternalService.save(userSignUpRequest));
        verify(userRepository, times(0)).save(user);
    }

    @Test
    @DisplayName("중복이 아닌 이메일은 정상 가입됩니다. 기본 구매자 ROLE")
    void save() {
        UserSignUpRequest userSignUpRequest = new UserSignUpRequest(email, name, nickName, password);
        given(userRepository.existsByEmail(email)).willReturn(false);
        given(passwordEncoder.encode(anyString())).willReturn(password.getValue());

        User userResponse = userInternalService.save(userSignUpRequest);

        verify(userRepository, times(1)).save(user);
        assertEquals(userResponse.getNickName(), nickName);
        assertEquals(userResponse.getName(), name);
        assertEquals(userResponse.getEmail(), email);
        assertEquals(userResponse.getUserRole(), UserRole.BUYER);
    }

    @Test
    @DisplayName("패스워드가 일치하면 로그인 됩니다.")
    void login() {
        UserLoginRequest userLoginRequest = new UserLoginRequest(email, password);
        given(userRepository.findByEmail(email)).willReturn(Optional.ofNullable(user));
        given(passwordEncoder.matches(anyString(), anyString())).willReturn(true);

        User userResponse = userInternalService.login(userLoginRequest);

        verify(userRepository).findByEmail(email);
        assertEquals(userResponse.getNickName(), nickName);
        assertEquals(userResponse.getName(), name);
        assertEquals(userResponse.getEmail(), email);
        assertEquals(userResponse.getUserRole(), UserRole.BUYER);
    }

    @Test
    @DisplayName("패스워드가 일치하지 않으면 Exception이 발생합니다.")
    void loginWithIncorrectPassword() {
        UserLoginRequest userLoginRequest = new UserLoginRequest(email, password);
        given(userRepository.findByEmail(email)).willReturn(Optional.ofNullable(user));
        given(passwordEncoder.matches(anyString(), anyString())).willReturn(false);

        assertThrows(InvalidPasswordException.class, () -> userInternalService.login(userLoginRequest));
        verify(userRepository).findByEmail(email);
    }

    @Test
    void withdraw() {
        given(userRepository.findById(anyLong())).willReturn(Optional.ofNullable(user));

        userInternalService.withdraw(1L);

        verify(userRepository).findById(1L);
    }

    @Test
    void isActiveUser() {
        assertTrue(userInternalService.isActiveUser(user));
    }

    @Test
    void isInactive() {
        assertTrue(userInternalService.isInactive(inactive));
    }

    @Test
    void isDormant() {
        assertTrue(userInternalService.isDormant(dormant));
    }
}