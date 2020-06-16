package dev.smjeon.commerce.user.dto;

import dev.smjeon.commerce.user.domain.Email;
import dev.smjeon.commerce.user.domain.NickName;
import dev.smjeon.commerce.user.domain.UserRole;
import dev.smjeon.commerce.user.domain.UserStatus;
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
    private UserStatus userStatus;

    public UserResponse(Long id, Email email, String name, NickName nickName, UserRole userRole, UserStatus userStatus) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.nickName = nickName;
        this.userRole = userRole;
        this.userStatus = userStatus;
    }
}
