package dev.smjeon.commerce.coupon.presentation;

import dev.smjeon.commerce.common.TestTemplate;
import dev.smjeon.commerce.coupon.domain.CouponType;
import dev.smjeon.commerce.coupon.dto.CouponRequest;
import dev.smjeon.commerce.coupon.dto.CouponResponse;
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
        CouponRequest couponRequest = new CouponRequest("쿠폰이름", "AABBCCDDEE12345", CouponType.PRODUCT, 0.05D);

        create(couponRequest)
                .jsonPath("$.couponName").isEqualTo("쿠폰이름")
                .jsonPath("$.code").isEqualTo("AABBCCDDEE12345")
                .jsonPath("$.type").isEqualTo("PRODUCT")
                .jsonPath("$.rate").isEqualTo(0.05);
    }

    @Test
    @DisplayName("중복된 코드로 쿠폰 생성 시 Access Denied")
    void createDuplicatedCoupon() {
        CouponRequest request = new CouponRequest("중복입니다", "12345AABBCCDDEE", CouponType.PRODUCT, 0.05D);
        create(request);
        CouponRequest duplicated = new CouponRequest("중복입니다", "12345AABBCCDDEE", CouponType.BASKET, 0.05D);

        loginAndRequest(HttpMethod.POST, "/api/coupon", duplicated, HttpStatus.FOUND,
                loginSessionId(adminLoginRequest.getEmail(), adminLoginRequest.getPassword()))
                .expectHeader()
                .value("Location", Matchers.containsString("/denied"));
    }

    @Test
    @DisplayName("ADMIN 권한이 아니면 쿠폰을 추가할 수 없습니다.")
    void createWithInsufficientAuthority() {
        CouponRequest request = new CouponRequest("쿠포온", "ABCDEABCDEABCDE", CouponType.PRODUCT, 0.05D);

        loginAndRequest(HttpMethod.POST, "/api/coupon", request, HttpStatus.FOUND,
                loginSessionId(sellerLoginRequest.getEmail(), sellerLoginRequest.getPassword()))
                .expectHeader()
                .value("Location", Matchers.containsString("/denied"));
    }

    private WebTestClient.BodyContentSpec create(CouponRequest request) {
        return respondApi(loginAndRequest(HttpMethod.POST, "/api/coupon", request, HttpStatus.CREATED,
                loginSessionId(adminLoginRequest.getEmail(), adminLoginRequest.getPassword())));
    }

    @Test
    @DisplayName("ADMIN 권한으로 쿠폰을 만료시킬 수 있습니다.")
    void expireCoupon() {
        CouponRequest request = new CouponRequest("만료될 쿠폰", "ABCDEABCDE12345", CouponType.PRODUCT, 0.05D);

        CouponResponse response = loginAndRequest(HttpMethod.POST, "/api/coupon", request, HttpStatus.CREATED,
                loginSessionId(adminLoginRequest.getEmail(), adminLoginRequest.getPassword()))
                .expectBody(CouponResponse.class)
                .returnResult()
                .getResponseBody();

        loginAndRequest(HttpMethod.DELETE, "/api/coupon/" + response.getId(), Void.class, HttpStatus.NO_CONTENT,
                loginSessionId(adminLoginRequest.getEmail(), adminLoginRequest.getPassword()));
    }

    @Test
    @DisplayName("ADMIN 권한이 아니면 쿠폰을 만료시킬 수 없습니다. (access deny)")
    void expireWithInsufficientAuthority() {
        CouponRequest request = new CouponRequest("만료 못할 쿠폰", "NEW1234567ABCDE", CouponType.PRODUCT, 0.05D);

        CouponResponse response = loginAndRequest(HttpMethod.POST, "/api/coupon", request, HttpStatus.CREATED,
                loginSessionId(adminLoginRequest.getEmail(), adminLoginRequest.getPassword()))
                .expectBody(CouponResponse.class)
                .returnResult()
                .getResponseBody();

        loginAndRequest(HttpMethod.DELETE, "/api/coupon/" + response.getId(), Void.class, HttpStatus.FOUND,
                loginSessionId(sellerLoginRequest.getEmail(), sellerLoginRequest.getPassword()))
                .expectHeader()
                .value("Location", Matchers.containsString("/denied"));
    }

    @Test
    @DisplayName("15자의 쿠폰 코드를 랜덤으로 받아옵니다.")
    void createRandomCode() {
        respondApi(loginAndRequest(HttpMethod.GET, "/api/coupon/code", Void.class, HttpStatus.OK,
                loginSessionId(adminLoginRequest.getEmail(), adminLoginRequest.getPassword())))
                .jsonPath("$.code").value(Matchers.hasLength(15));
    }
}
