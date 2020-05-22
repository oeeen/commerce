package dev.smjeon.commerce.user.dto;

import dev.smjeon.commerce.user.domain.Email;
import dev.smjeon.commerce.user.domain.NickName;
import dev.smjeon.commerce.user.domain.Password;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class UserSignUpRequest {
    private Email email;
    private String userName;
    private NickName nickName;
    private Password password;

    public UserSignUpRequest(Email email, String userName, NickName nickName, Password password) {
        this.email = email;
        this.userName = userName;
        this.nickName = nickName;
        this.password = password;
    }
}
