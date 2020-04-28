package dev.smjeon.commerce.category.domain;

import dev.smjeon.commerce.common.BaseEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;

@NoArgsConstructor
@Getter
@Entity(name = "category")
public class TopCategory extends BaseEntity {

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
