package dev.smjeon.commerce.coupon.repository;

import dev.smjeon.commerce.coupon.domain.Coupon;
import dev.smjeon.commerce.coupon.domain.CouponCode;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CouponRepository extends JpaRepository<Coupon, Long> {
    Optional<Coupon> findByCode(CouponCode code);
}
