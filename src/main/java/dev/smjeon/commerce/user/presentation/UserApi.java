package dev.smjeon.commerce.user.presentation;

import dev.smjeon.commerce.user.application.UserService;
import dev.smjeon.commerce.user.dto.UserLoginRequest;
import dev.smjeon.commerce.user.dto.UserResponse;
import dev.smjeon.commerce.user.dto.UserSignUpRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/api/users")
@RestController
public class UserApi {
    private final UserService userService;

    public UserApi(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<UserResponse>> findAll() {
        return ResponseEntity.ok(userService.findAll());
    }

    @PostMapping("/signup")
    public ResponseEntity<UserResponse> save(@RequestBody UserSignUpRequest userSignUpRequest) {
        UserResponse signUpUser = userService.save(userSignUpRequest);

        return ResponseEntity.ok(signUpUser);
    }

    @PostMapping("/signin")
    public ResponseEntity<UserResponse> login(UserLoginRequest userLoginRequest) {
        UserResponse loginUser = userService.login(userLoginRequest);

        return ResponseEntity.ok(loginUser);
    }

    @DeleteMapping("{userId}")
    public ResponseEntity<Void> withdraw(@PathVariable Long userId) {
        userService.withdraw(userId);

        return ResponseEntity.noContent().build();
    }
}
