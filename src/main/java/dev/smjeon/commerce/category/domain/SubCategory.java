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
public class SubCategory {

    @AttributeOverrides({
            @AttributeOverride(name = "value", column = @Column(name = "sub_category"))
    })
    @Embedded
    private CategoryName name;

    @Embedded
    private LowestCategory lowestCategory;

    public SubCategory(CategoryName name, LowestCategory lowestCategory) {
        this.name = name;
        this.lowestCategory = lowestCategory;
    }
}
