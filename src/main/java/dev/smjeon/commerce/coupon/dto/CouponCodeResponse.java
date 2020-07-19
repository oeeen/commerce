package dev.smjeon.commerce.coupon.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class CouponCodeResponse {
    private String code;

    public CouponCodeResponse(String code) {
        this.code = code;
    }
}
