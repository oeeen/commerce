package dev.smjeon.commerce.oauth.common.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SocialUserRepository extends JpaRepository<SocialLoginUser, Long> {

    Optional<SocialLoginUser> findByOauthId(String oauthId);
}
