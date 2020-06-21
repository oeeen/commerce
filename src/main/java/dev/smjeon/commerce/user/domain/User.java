package dev.smjeon.commerce.user.domain;

import dev.smjeon.commerce.common.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.LocalDate;
import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Entity
public class User extends BaseEntity {

    @Embedded
    private Email email;

    @Embedded
    private Password password;

    @Column(nullable = false)
    private String name;

    @Embedded
    private NickName nickName;

    @Enumerated
    private Gender gender;

    private Integer age;

    private LocalDate birthday;

    @Enumerated(value = EnumType.STRING)
    private UserRole userRole;

    @Enumerated(value = EnumType.STRING)
    private UserStatus userStatus;

    private LocalDateTime lastLogin;

    public User(Email email, Password password, String name, NickName nickName, UserRole userRole, UserStatus userStatus) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.nickName = nickName;
        this.userRole = userRole;
        this.userStatus = userStatus;
    }

    public void updateGender(Gender gender) {
        this.gender = gender;
    }

    public void updateAge(int age) {
        this.age = age;
    }

    public void updateBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public void deactivate() {
        this.userStatus = UserStatus.INACTIVE;
    }

    public void activate() {
        this.userStatus = UserStatus.ACTIVE;
    }

    public boolean isActive() {
        return UserStatus.ACTIVE.equals(this.userStatus);
    }

    public boolean isAdmin() {
        return UserRole.ADMIN.equals(this.userRole);
    }

    public void signUp() {
        this.lastLogin = getCreatedTime();
    }

    public void login() {
        this.lastLogin = LocalDateTime.now();
    }
}
