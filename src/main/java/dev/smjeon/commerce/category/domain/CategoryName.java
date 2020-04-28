package dev.smjeon.commerce.category.domain;

import dev.smjeon.commerce.category.exception.InvalidCategoryNameException;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@NoArgsConstructor
@Getter
@EqualsAndHashCode(of = "value")
@Embeddable
public class CategoryName {
    private static final String PATTERN = "[0-9a-zA-Zㄱ-ㅎㅏ-ㅣㅍ가-힣]*";

    @Column(nullable = false)
    private String value;

    public CategoryName(String value) {
        validate(value);
        this.value = value;
    }

    private void validate(String value) {
        Pattern pattern = Pattern.compile(PATTERN);
        Matcher matcher = pattern.matcher(value);

        if (!matcher.matches()) {
            throw new InvalidCategoryNameException(value);
        }
    }
}
