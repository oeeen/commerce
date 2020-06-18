package dev.smjeon.commerce.product.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import dev.smjeon.commerce.product.exception.InvalidBrandNameException;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Transient;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@NoArgsConstructor
@Getter
@EqualsAndHashCode(of = {"brandName", "productName"})
@Embeddable
public class ProductName {
    private static final String PATTERN = "[0-9a-zA-Zㄱ-ㅎㅏ-ㅣ가-힣]*";

    @JsonIgnore
    @Transient
    private final Pattern pattern = Pattern.compile(PATTERN);

    @Column(nullable = false)
    private String brandName;

    @Column(nullable = false)
    private String productName;

    public ProductName(String brandName, String productName) {
        validate(brandName);
        this.brandName = brandName;
        this.productName = productName;
    }

    private void validate(String brandName) {
        Matcher matcher = pattern.matcher(brandName);

        if (!matcher.matches()) {
            throw new InvalidBrandNameException(brandName);
        }
    }
}
