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
public class TopCategory {

    @AttributeOverrides({
            @AttributeOverride(name = "value", column = @Column(name = "top_category"))
    })
    @Embedded
    private CategoryName name;

    @Embedded
    private SubCategory subCategory;

    public TopCategory(CategoryName name, SubCategory subCategory) {
        this.name = name;
        this.subCategory = subCategory;
    }
}
