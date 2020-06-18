package dev.smjeon.commerce.user.application;

import dev.smjeon.commerce.security.UserContext;
import dev.smjeon.commerce.user.converter.UserConverter;
import dev.smjeon.commerce.user.domain.Email;
import dev.smjeon.commerce.user.domain.User;
import dev.smjeon.commerce.user.dto.UserLoginRequest;
import dev.smjeon.commerce.user.dto.UserResponse;
import dev.smjeon.commerce.user.dto.UserSignUpRequest;
import dev.smjeon.commerce.user.dto.UserWithdrawRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserInternalService userInternalService;

    public UserService(UserInternalService userInternalService) {
        this.userInternalService = userInternalService;
    }

    public List<UserResponse> findAll() {
        List<User> users = userInternalService.findAll();
        return users.stream()
                .map(UserConverter::toDto)
                .collect(Collectors.toList());
    }

    public UserContext findByEmail(Email email) {
        return userInternalService.findByEmail(email);
    }

    public UserResponse save(UserSignUpRequest userSignUpRequest) {
        User user = userInternalService.save(userSignUpRequest);
        return UserConverter.toDto(user);
    }

    public UserResponse login(UserLoginRequest userLoginRequest) {
        User user = userInternalService.login(userLoginRequest);
        return UserConverter.toDto(user);
    }

    public void withdraw(Long userId) {
        userInternalService.withdraw(userId);
    }

    public boolean checkPassword(UserWithdrawRequest userWithdrawRequest) {
        return userInternalService.checkPassword(userWithdrawRequest);
    }
}
