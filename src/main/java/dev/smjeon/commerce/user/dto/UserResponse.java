package dev.smjeon.commerce.user.dto;

import dev.smjeon.commerce.user.domain.Email;
import dev.smjeon.commerce.user.domain.NickName;
import lombok.Getter;

@Getter
public class UserResponse {
    private Email email;
    private String name;
    private NickName nickName;
}
