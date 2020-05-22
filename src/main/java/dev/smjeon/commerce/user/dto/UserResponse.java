package dev.smjeon.commerce.user.dto;

import dev.smjeon.commerce.user.domain.Email;
import dev.smjeon.commerce.user.domain.NickName;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class UserResponse {
    private Email email;
    private String name;
    private NickName nickName;

    public UserResponse(Email email, String name, NickName nickName) {
        this.email = email;
        this.name = name;
        this.nickName = nickName;
    }
}
