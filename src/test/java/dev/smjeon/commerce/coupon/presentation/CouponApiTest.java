package dev.smjeon.commerce.coupon.presentation;

import dev.smjeon.commerce.common.TestTemplate;
import dev.smjeon.commerce.coupon.domain.CouponType;
import dev.smjeon.commerce.coupon.dto.CouponRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

public class CouponApiTest extends TestTemplate {

    @Test
    @DisplayName("ADMIN 권한으로 쿠폰을 추가할 수 있습니다.")
    void createCoupon() {
        CouponRequest couponRequest = new CouponRequest("쿠폰이름", "쿠폰코드", CouponType.PRODUCT, 0.05D);

        respondApi(loginAndRequest(HttpMethod.POST, "/api/coupon", couponRequest, HttpStatus.CREATED,
                loginSessionId(adminLoginRequest.getEmail(), adminLoginRequest.getPassword())))
                .jsonPath("$.couponName").isEqualTo("쿠폰이름")
                .jsonPath("$.code").isEqualTo("쿠폰코드")
                .jsonPath("$.type").isEqualTo("PRODUCT")
                .jsonPath("$.rate").isEqualTo(0.05);
    }
}
