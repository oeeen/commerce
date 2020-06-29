package dev.smjeon.commerce.coupon.presentation;

import dev.smjeon.commerce.coupon.application.CouponService;
import dev.smjeon.commerce.coupon.dto.CouponRequest;
import dev.smjeon.commerce.coupon.dto.CouponResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RequestMapping("/api/coupon")
@RestController
public class CouponApi {

    private final CouponService couponService;

    public CouponApi(CouponService couponService) {
        this.couponService = couponService;
    }

    @PostMapping
    public ResponseEntity<CouponResponse> create(@RequestBody CouponRequest couponRequest) {
        CouponResponse response = couponService.create(couponRequest);
        return ResponseEntity.created(URI.create("/api/coupon/" + response.getId())).body(response);
    }
}
