package dev.smjeon.commerce.category.presentation;

import dev.smjeon.commerce.category.dto.CategoryRequest;
import dev.smjeon.commerce.common.TestTemplate;
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

    @Test
    @DisplayName("관리자 권한으로 카테고리 추가가 가능합니다.")
    void createCategory() {
        String lowestCategory = "최하위 카테고리";
        String subCategory = "중간 카테고리";
        String topCategory = "최상위 카테고리";
        CategoryRequest categoryRequest = new CategoryRequest(topCategory, subCategory, lowestCategory);
        respondApi(loginAndRequest(HttpMethod.POST, "/api/categories", categoryRequest, HttpStatus.CREATED,
                loginSessionId(adminLoginRequest.getEmail(), adminLoginRequest.getPassword())))
                .jsonPath("$.topCategory").isEqualTo("최상위 카테고리")
                .jsonPath("$.subCategory").isEqualTo("중간 카테고리")
                .jsonPath("$.lowestCategory").isEqualTo("최하위 카테고리");
    }
}
