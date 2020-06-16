package dev.smjeon.commerce.user.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class UserWithdrawRequest {
    private Long id;
    private String password;

    public UserWithdrawRequest(Long id, String password) {
        this.id = id;
        this.password = password;
    }
}
