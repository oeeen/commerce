package dev.smjeon.commerce.coupon.application;

import dev.smjeon.commerce.common.WithMockCustomUser;
import dev.smjeon.commerce.coupon.domain.Coupon;
import dev.smjeon.commerce.coupon.domain.CouponCode;
import dev.smjeon.commerce.coupon.domain.CouponStatus;
import dev.smjeon.commerce.coupon.domain.CouponType;
import dev.smjeon.commerce.coupon.dto.CouponRequest;
import dev.smjeon.commerce.coupon.exception.DuplicatedCouponException;
import dev.smjeon.commerce.coupon.exception.NotFoundCouponException;
import dev.smjeon.commerce.coupon.repository.CouponRepository;
import dev.smjeon.commerce.user.domain.UserRole;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
class CouponInternalServiceTest {

    @InjectMocks
    private CouponInternalService couponInternalService;

    @Mock
    private CouponRepository couponRepository;

    @Mock
    private Coupon coupon;

    @Test
    @WithMockCustomUser(role = UserRole.ADMIN)
    @DisplayName("쿠폰 코드가 중복이 아닐 경우 정상 생성")
    void create() {
        given(couponRepository.findByCode(new CouponCode("CODE"))).willReturn(Optional.empty());


        CouponRequest couponRequest = new CouponRequest("이름", "CODE", CouponType.BASKET, 0.5);
        couponInternalService.create(couponRequest);

        verify(couponRepository).findByCode(new CouponCode("CODE"));
    }

    @Test
    @WithMockCustomUser(role = UserRole.ADMIN)
    @DisplayName("쿠폰 코드가 중복일 경우 DuplicatedCouponException 발생")
    void createDuplicated() {
        given(couponRepository.findByCode(new CouponCode("CODE"))).willReturn(Optional.of(coupon));

        CouponRequest request = new CouponRequest("쿠폰", "CODE", CouponType.BASKET, 0.5);
        assertThrows(DuplicatedCouponException.class, () -> couponInternalService.create(request));

        verify(couponRepository).findByCode(new CouponCode("CODE"));
    }

    @Test
    @WithMockCustomUser(role = UserRole.ADMIN)
    @DisplayName("쿠폰을 만료시키면 상태가 EXPIRED로 변경됩니다.")
    void expire() {
        Coupon requestedCoupon = new Coupon(
                "쿠폰",
                new CouponCode("Code"),
                CouponType.BASKET,
                0.5,
                CouponStatus.NORMAL
        );
        given(couponRepository.findById(1L)).willReturn(Optional.of(requestedCoupon));


        assertEquals(requestedCoupon.getStatus(), CouponStatus.NORMAL);
        couponInternalService.expire(1L);
        assertEquals(requestedCoupon.getStatus(), CouponStatus.EXPIRED);
        verify(couponRepository).findById(1L);
    }

    @Test
    @WithMockCustomUser(role = UserRole.ADMIN)
    @DisplayName("존재하지 않는 쿠폰일 경우 NotFoundCouponException이 발생합니다.")
    void expireNotFoundCouponId() {
        given(couponRepository.findById(1L)).willReturn(Optional.empty());

        assertThrows(NotFoundCouponException.class, () -> couponInternalService.expire(1L));
        verify(couponRepository).findById(1L);
    }
}