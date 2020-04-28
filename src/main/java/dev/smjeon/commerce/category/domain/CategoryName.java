package dev.smjeon.commerce.category.domain;

import dev.smjeon.commerce.category.exception.InvalidCategoryNameException;
import lombok.EqualsAndHashCode;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@EqualsAndHashCode(of = "value")
public class CategoryName {
    private static final String PATTERN = "[0-9a-zA-Zㄱ-ㅎㅏ-ㅣㅍ가-힣]*";

    private final String value;

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
