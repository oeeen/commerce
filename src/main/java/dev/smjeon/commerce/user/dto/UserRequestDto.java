package dev.smjeon.commerce.user.dto;

import dev.smjeon.commerce.user.domain.Email;
import dev.smjeon.commerce.user.domain.NickName;
import dev.smjeon.commerce.user.domain.Password;
import dev.smjeon.commerce.user.domain.UserRole;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class UserRequestDto {
    private Email email;
    private String userName;
    private NickName nickName;
    private Password password;
    private UserRole userRole;

    public UserRequestDto(Email email, String userName, NickName nickName, Password password) {
        this.email = email;
        this.userName = userName;
        this.nickName = nickName;
        this.password = password;
        this.userRole = UserRole.BUYER;
    }
}
