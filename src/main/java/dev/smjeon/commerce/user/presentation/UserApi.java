package dev.smjeon.commerce.user.presentation;

import dev.smjeon.commerce.user.application.UserService;
import dev.smjeon.commerce.user.dto.UserRequestDto;
import dev.smjeon.commerce.user.dto.UserResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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
    public ResponseEntity<List<UserResponseDto>> findAll() {
        return ResponseEntity.ok(userService.findAll());
    }

    @PostMapping("/signup")
    public ResponseEntity<UserResponseDto> save(@RequestBody UserRequestDto userRequestDto) {
        UserResponseDto signUpUser = userService.save(userRequestDto);

        return ResponseEntity.ok(signUpUser);
    }
}
