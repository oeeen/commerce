package dev.smjeon.commerce.category.presentation;

import dev.smjeon.commerce.category.dto.CategoryRequest;
import dev.smjeon.commerce.common.TestTemplate;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

public class CategoryApiTest extends TestTemplate {
    private CategoryRequest categoryRequest;

    @BeforeEach
    void setUp() {
        String lowestCategory = "최하위 카테고리";
        String subCategory = "중간 카테고리";
        String topCategory = "최상위 카테고리";
        categoryRequest = new CategoryRequest(topCategory, subCategory, lowestCategory);
    }

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
        respondApi(loginAndRequest(HttpMethod.POST, "/api/categories", categoryRequest, HttpStatus.CREATED,
                loginSessionId(adminLoginRequest.getEmail(), adminLoginRequest.getPassword())))
                .jsonPath("$.topCategory").isEqualTo("최상위 카테고리")
                .jsonPath("$.subCategory").isEqualTo("중간 카테고리")
                .jsonPath("$.lowestCategory").isEqualTo("최하위 카테고리");
    }

    @Test
    @DisplayName("관리자가 아닌 사용자는 카테고리 추가가 불가능합니다.")
    void createCategoryWithInsufficientAuthority() {
        loginAndRequest(HttpMethod.POST, "/api/categories", categoryRequest, HttpStatus.FOUND,
                loginSessionId(sellerLoginRequest.getEmail(), sellerLoginRequest.getPassword()))
                .expectHeader()
                .value("Location", Matchers.containsString("/denied"));
    }

    @Test
    @DisplayName("이미 존재하는 카테고리를 생성하면 Access Denied 됩니다. ")
    void createDuplicatedCategory() {
        String lowestCategory = "쌀";
        String subCategory = "신선식품";
        String topCategory = "식품";
        CategoryRequest categoryRequest = new CategoryRequest(topCategory, subCategory, lowestCategory);

        loginAndRequest(HttpMethod.POST, "/api/categories", categoryRequest, HttpStatus.FOUND,
                loginSessionId(adminLoginRequest.getEmail(), adminLoginRequest.getPassword()))
                .expectHeader()
                .value("Location", Matchers.containsString("/denied"));
    }

    @Test
    @DisplayName("3개의 카테고리가 모두 중복이 아니면 새 카테고리로 추가합니다.")
    void createDuplicatedTopCategory() {
        String lowestCategory = "새 카테고리";
        String subCategory = "신선식품";
        String topCategory = "식품";
        CategoryRequest categoryRequest = new CategoryRequest(topCategory, subCategory, lowestCategory);

        respondApi(loginAndRequest(HttpMethod.POST, "/api/categories", categoryRequest, HttpStatus.CREATED,
                loginSessionId(adminLoginRequest.getEmail(), adminLoginRequest.getPassword())))
                .jsonPath("$.topCategory").isEqualTo(topCategory)
                .jsonPath("$.subCategory").isEqualTo(subCategory)
                .jsonPath("$.lowestCategory").isEqualTo(lowestCategory);
    }
}
