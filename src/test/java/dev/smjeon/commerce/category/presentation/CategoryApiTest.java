package dev.smjeon.commerce.category.presentation;

import dev.smjeon.commerce.user.presentation.TestTemplate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

public class CategoryApiTest extends TestTemplate {

    @Test
    @DisplayName("아무 권한이 없어도 카테고리 조회가 가능합니다.")
    void read() {
        respondApi(request(HttpMethod.GET, "/api/categories", Void.class, HttpStatus.OK))
                .jsonPath("$.[0].topCategory").isEqualTo("식품")
                .jsonPath("$.[0].subCategory").isEqualTo("신선식품")
                .jsonPath("$.[0].lowestCategory").isEqualTo("쌀");
    }
}
