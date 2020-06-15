package dev.smjeon.commerce.user.dto;

import dev.smjeon.commerce.user.domain.Email;
import dev.smjeon.commerce.user.domain.NickName;
import dev.smjeon.commerce.user.domain.UserRole;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class UserResponse {
    private Long id;
    private Email email;
    private String name;
    private NickName nickName;
    private UserRole userRole;

    public UserResponse(Long id, Email email, String name, NickName nickName, UserRole userRole) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.nickName = nickName;
        this.userRole = userRole;
    }
}
