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

    private int age;

    private LocalDate birthday;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false, name = "user_role")
    private UserRole userRole;

    public User(Email email, Password password, String name, NickName nickName) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.nickName = nickName;
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
}
