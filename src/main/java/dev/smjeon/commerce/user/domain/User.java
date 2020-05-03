package dev.smjeon.commerce.user.domain;

import dev.smjeon.commerce.common.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import java.time.LocalDate;

@NoArgsConstructor
@Getter
@Entity
public class User extends BaseEntity {

    @Embedded
    private Email email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String nickName;

    private String gender;

    private int age;

    private LocalDate birthday;

    public User(Email email, String password, String name, String nickName) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.nickName = nickName;
    }

    public void updateGender(String gender) {
        this.gender = gender;
    }

    public void updateAge(int age) {
        this.age = age;
    }

    public void updateBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }
}
