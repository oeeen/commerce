package dev.smjeon.commerce.category.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;

@NoArgsConstructor
@Getter
@Embeddable
public class LowestCategory {

    @AttributeOverrides({
            @AttributeOverride(name = "value", column = @Column(name = "lowest_category"))
    })
    @Embedded
    private CategoryName name;

    public LowestCategory(CategoryName name) {
        this.name = name;
    }

    public String getCategoryNameValue() {
        return this.name.getValue();
    }
}
