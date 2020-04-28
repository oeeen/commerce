package dev.smjeon.commerce.product.domain;

import dev.smjeon.commerce.product.domain.exception.InvalidCategoryNameException;
import lombok.EqualsAndHashCode;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@EqualsAndHashCode(of = "value")
public class CategoryName {
    private static final String PATTERN = "[0-9|a-z|A-Z|ㄱ-ㅎ|ㅏ-ㅣ|가-힣]*";

    private final String value;

    public CategoryName(String value) {
        validate(value);
        this.value = value;
    }

    private void validate(String value) {
        Pattern pattern = Pattern.compile(PATTERN);
        Matcher matcher = pattern.matcher(value);

        if (!matcher.matches()) {
            throw new InvalidCategoryNameException();
        }
    }
}
