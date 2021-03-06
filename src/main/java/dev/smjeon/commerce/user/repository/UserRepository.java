package dev.smjeon.commerce.user.repository;

import dev.smjeon.commerce.user.domain.Email;
import dev.smjeon.commerce.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(Email email);

    boolean existsByEmail(Email email);
}
