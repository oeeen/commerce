package dev.smjeon.commerce.security;


import dev.smjeon.commerce.user.domain.UserRole;
import lombok.Getter;

@Getter
public class UserContext {

    private Long id;

    private String oauthId;

    private String name;

    private UserRole userRole;

    public UserContext(Long id, String oauthId, String name, UserRole userRole) {
        this.id = id;
        this.oauthId = oauthId;
        this.name = name;
        this.userRole = userRole;
    }
}
