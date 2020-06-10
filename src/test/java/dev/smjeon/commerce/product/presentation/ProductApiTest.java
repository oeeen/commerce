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

    @Test
    @DisplayName("권한 없이 특정 카테고리의 모든 상품 리스트를 조회합니다.")
    void findProductsByCategory() {
        respondApi(request(HttpMethod.GET, "/api/products/categories/10", Void.class, HttpStatus.OK))
                .jsonPath("$.[0].brandName").isEqualTo("수미네")
                .jsonPath("$.[0].productName").isEqualTo("장조림")
                .jsonPath("$.[0].topCategory").isEqualTo("식품")
                .jsonPath("$.[0].subCategory").isEqualTo("신선식품")
                .jsonPath("$.[0].lowestCategory").isEqualTo("반찬")
                .jsonPath("$.[0].price").isEqualTo(6000)
                .jsonPath("$.[0].shippingFee").isEqualTo(2500);
    }

    @Test
    @DisplayName("권한 없이 이벤트 상품 리스트를 조회합니다.")
    void findEventProducts() {
        respondApi(request(HttpMethod.GET, "/api/products/event", Void.class, HttpStatus.OK))
                .jsonPath("$.[0].brandName").isEqualTo("이벤트")
                .jsonPath("$.[0].productName").isEqualTo("헤드셋")
                .jsonPath("$.[0].topCategory").isEqualTo("컴퓨터")
                .jsonPath("$.[0].subCategory").isEqualTo("PC주변기기")
                .jsonPath("$.[0].lowestCategory").isEqualTo("PC영상/음향장치")
                .jsonPath("$.[0].price").isEqualTo(10000)
                .jsonPath("$.[0].shippingFee").isEqualTo(0);
    }
}
