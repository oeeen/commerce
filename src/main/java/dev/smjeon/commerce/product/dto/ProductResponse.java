package dev.smjeon.commerce.product.dto;

import dev.smjeon.commerce.user.dto.UserResponse;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class ProductResponse {
    private Long id;
    private String brandName;
    private String productName;
    private String topCategory;
    private String subCategory;
    private String lowestCategory;
    private int price;
    private int shippingFee;
    private UserResponse userResponse;

    public ProductResponse(Long id, String brandName, String productName, String topCategory,
                           String subCategory, String lowestCategory, int price, int shippingFee, UserResponse userResponse) {
        this.id = id;
        this.brandName = brandName;
        this.productName = productName;
        this.topCategory = topCategory;
        this.subCategory = subCategory;
        this.lowestCategory = lowestCategory;
        this.price = price;
        this.shippingFee = shippingFee;
        this.userResponse = userResponse;
    }
}
