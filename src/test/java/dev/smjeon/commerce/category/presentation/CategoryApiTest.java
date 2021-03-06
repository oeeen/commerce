package dev.smjeon.commerce.category.presentation;

import dev.smjeon.commerce.category.dto.CategoryRequest;
import dev.smjeon.commerce.category.dto.CategoryResponse;
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

    @Test
    @DisplayName("관리자 권한으로 카테고리 수정이 가능합니다.")
    void updateCategory() {
        String lowestCategory = "수정 전 카테고리1";
        String subCategory = "수정 전 카테고리2";
        String topCategory = "수정 전 카테고리3";
        CategoryRequest categoryRequest = new CategoryRequest(topCategory, subCategory, lowestCategory);
        CategoryResponse response = createCategoryFromRequest(categoryRequest);

        lowestCategory = "수정 후 카테고리1";
        subCategory = "수정 후 카테고리2";
        topCategory = "수정 후 카테고리3";
        CategoryRequest updateRequest = new CategoryRequest(topCategory, subCategory, lowestCategory);

        respondApi(loginAndRequest(HttpMethod.PUT, "/api/categories/" + response.getId(), updateRequest, HttpStatus.OK,
                loginSessionId(adminLoginRequest.getEmail(), adminLoginRequest.getPassword())))
                .jsonPath("$.topCategory").isEqualTo("수정 후 카테고리3")
                .jsonPath("$.subCategory").isEqualTo("수정 후 카테고리2")
                .jsonPath("$.lowestCategory").isEqualTo("수정 후 카테고리1");
    }

    @Test
    @DisplayName("이미 존재하는 카테고리로 수정하려고 하면 Access Denied 됩니다.")
    void updateDuplicatedCategory() {
        String lowestCategory = "쌀";
        String subCategory = "신선식품";
        String topCategory = "식품";
        CategoryRequest categoryRequest = new CategoryRequest(topCategory, subCategory, lowestCategory);

        loginAndRequest(HttpMethod.PUT, "/api/categories/1", categoryRequest, HttpStatus.FOUND,
                loginSessionId(adminLoginRequest.getEmail(), adminLoginRequest.getPassword()))
                .expectHeader()
                .value("Location", Matchers.containsString("/denied"));

    }

    @Test
    @DisplayName("관리자가 아닌 사용자는 카테고리 수정이 불가능합니다.")
    void updateCategoryWithInsufficientAuthority() {
        String lowestCategory = "판매자 카테고리";
        String subCategory = "판매자";
        String topCategory = "최상위 판매자 카테고리";
        CategoryRequest categoryRequest = new CategoryRequest(topCategory, subCategory, lowestCategory);

        loginAndRequest(HttpMethod.POST, "/api/categories", categoryRequest, HttpStatus.FOUND,
                loginSessionId(sellerLoginRequest.getEmail(), sellerLoginRequest.getPassword()))
                .expectHeader()
                .value("Location", Matchers.containsString("/denied"));

    }

    @Test
    @DisplayName("관리자 권한으로 카테고리를 삭제할 수 있습니다.")
    void deleteCategory() {
        CategoryRequest request = new CategoryRequest("삭제할 카테고리", "삭제", "삭제할");
        CategoryResponse response = createCategoryFromRequest(request);

        loginAndRequest(HttpMethod.DELETE, "/api/categories/" + response.getId(), Void.class, HttpStatus.NO_CONTENT,
                loginSessionId(adminLoginRequest.getEmail(), adminLoginRequest.getPassword()));
    }

    @Test
    @DisplayName("관리자가 아닌 사용자는 카테고리를 삭제할 수 없습니다.")
    void deleteCategoryWithInsufficientAuthority() {
        CategoryRequest request = new CategoryRequest("삭제 못하는", "카테", "고리");
        CategoryResponse response = createCategoryFromRequest(request);

        loginAndRequest(HttpMethod.DELETE, "/api/categories/" + response.getId(), Void.class, HttpStatus.FOUND,
                loginSessionId(buyerLoginRequest.getEmail(), buyerLoginRequest.getPassword()))
                .expectHeader()
                .value("Location", Matchers.containsString("/denied"));
    }

    private CategoryResponse createCategoryFromRequest(CategoryRequest categoryRequest) {
        return loginAndRequest(HttpMethod.POST, "/api/categories", categoryRequest, HttpStatus.CREATED,
                loginSessionId(adminLoginRequest.getEmail(), adminLoginRequest.getPassword()))
                .expectBody(CategoryResponse.class)
                .returnResult()
                .getResponseBody();
    }
}
