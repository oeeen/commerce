package dev.smjeon.commerce.user.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@NoArgsConstructor
@Getter
@Embeddable
public class NickName {

    @Column(nullable = false, name = "nick_name")
    private String nickName;

    public NickName(String nickName) {
        this.nickName = nickName;
    }
}
