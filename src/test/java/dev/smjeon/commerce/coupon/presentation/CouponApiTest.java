package dev.smjeon.commerce.coupon.presentation;

import dev.smjeon.commerce.common.TestTemplate;
import dev.smjeon.commerce.coupon.domain.CouponType;
import dev.smjeon.commerce.coupon.dto.CouponRequest;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.reactive.server.WebTestClient;

public class CouponApiTest extends TestTemplate {

    @Test
    @DisplayName("ADMIN 권한으로 쿠폰을 추가할 수 있습니다.")
    void createCoupon() {
        CouponRequest couponRequest = new CouponRequest("쿠폰이름", "쿠폰코드", CouponType.PRODUCT, 0.05D);

        create(couponRequest)
                .jsonPath("$.couponName").isEqualTo("쿠폰이름")
                .jsonPath("$.code").isEqualTo("쿠폰코드")
                .jsonPath("$.type").isEqualTo("PRODUCT")
                .jsonPath("$.rate").isEqualTo(0.05);
    }

    @Test
    @DisplayName("중복된 코드로 쿠폰 생성 시 Access Denied")
    void createDuplicatedCoupon() {
        CouponRequest request = new CouponRequest("중복입니다", "중복코드", CouponType.PRODUCT, 0.05D);
        create(request);
        CouponRequest duplicated = new CouponRequest("중복입니다", "중복코드", CouponType.BASKET, 0.05D);

        loginAndRequest(HttpMethod.POST, "/api/coupon", duplicated, HttpStatus.FOUND,
                loginSessionId(adminLoginRequest.getEmail(), adminLoginRequest.getPassword()))
                .expectHeader()
                .value("Location", Matchers.containsString("/denied"));
    }

    @Test
    @DisplayName("ADMIN 권한이 아니면 쿠폰을 추가할 수 없습니다.")
    void createWithInsufficientAuthority() {
        CouponRequest request = new CouponRequest("쿠포온", "코드", CouponType.PRODUCT, 0.05D);

        loginAndRequest(HttpMethod.POST, "/api/coupon", request, HttpStatus.FOUND,
                loginSessionId(sellerLoginRequest.getEmail(), sellerLoginRequest.getPassword()))
                .expectHeader()
                .value("Location", Matchers.containsString("/denied"));
    }

    private WebTestClient.BodyContentSpec create(CouponRequest request) {
        return respondApi(loginAndRequest(HttpMethod.POST, "/api/coupon", request, HttpStatus.CREATED,
                loginSessionId(adminLoginRequest.getEmail(), adminLoginRequest.getPassword())));
    }
}
