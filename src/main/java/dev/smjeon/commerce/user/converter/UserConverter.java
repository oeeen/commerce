package dev.smjeon.commerce.user.converter;

import dev.smjeon.commerce.user.domain.User;
import dev.smjeon.commerce.user.dto.UserResponse;

public class UserConverter {
    private UserConverter() {
    }

    public static UserResponse toDto(User user) {
        return new UserResponse(
                user.getId(),
                user.getEmail(),
                user.getName(),
                user.getNickName(),
                user.getUserRole(),
                user.getUserStatus());
    }
}
