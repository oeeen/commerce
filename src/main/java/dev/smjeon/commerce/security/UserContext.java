package dev.smjeon.commerce.security;


import dev.smjeon.commerce.user.domain.UserRole;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
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
