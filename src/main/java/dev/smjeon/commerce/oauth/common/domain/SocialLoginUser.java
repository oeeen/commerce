package dev.smjeon.commerce.oauth.common.domain;

import dev.smjeon.commerce.common.BaseEntity;
import dev.smjeon.commerce.user.domain.Email;
import dev.smjeon.commerce.user.domain.NickName;
import dev.smjeon.commerce.user.domain.UserRole;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;


@NoArgsConstructor
@Getter
@Entity
public class SocialLoginUser extends BaseEntity {

    @Column(nullable = false)
    private String oauthId;

    @Embedded
    private Email email;

    @Embedded
    private NickName nickName;

    @Enumerated(value = EnumType.STRING)
    private UserRole userRole;

    public SocialLoginUser(String oauthId, Email email, NickName nickName, UserRole userRole) {
        this.oauthId = oauthId;
        this.email = email;
        this.nickName = nickName;
        this.userRole = userRole;
    }

    public void updateUserRole(UserRole role) {
        this.userRole = role;
    }
}
