package dev.smjeon.commerce.category.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;

@NoArgsConstructor
@Getter
@EqualsAndHashCode(of = {"name", "lowestCategory"})
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

    public String getCategoryNameValue() {
        return this.name.getValue();
    }

    public String getLowestCategoryValue() {
        return this.lowestCategory.getCategoryNameValue();
    }
}
