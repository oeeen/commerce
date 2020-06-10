package dev.smjeon.commerce.product.presentation;

import dev.smjeon.commerce.user.presentation.TestTemplate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

public class ProductApiTest extends TestTemplate {

    @Test
    @DisplayName("권한 없이 모든 상품 리스트를 조회합니다.")
    void findAll() {
        respondApi(request(HttpMethod.GET, "/api/products", Void.class, HttpStatus.OK))
                .jsonPath("$.[0].brandName").isEqualTo("마틴")
                .jsonPath("$.[0].productName").isEqualTo("좋은 쌀")
                .jsonPath("$.[0].topCategory").isEqualTo("식품")
                .jsonPath("$.[0].subCategory").isEqualTo("신선식품")
                .jsonPath("$.[0].lowestCategory").isEqualTo("쌀")
                .jsonPath("$.[0].price").isEqualTo(30000)
                .jsonPath("$.[0].shippingFee").isEqualTo(3000);
    }
}
