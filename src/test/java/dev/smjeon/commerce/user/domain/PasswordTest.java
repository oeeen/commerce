package dev.smjeon.commerce.user.domain;

import dev.smjeon.commerce.user.exception.InvalidPasswordException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class PasswordTest {
    @ParameterizedTest
    @MethodSource("provideStringsForNormalPassword")
    @DisplayName("정상적으로 패스워드가 생성됩니다.")
    void normalPassword(String input) {
        assertDoesNotThrow(() -> new Password(input));
    }

    private static Stream<String> provideStringsForNormalPassword() {
        return
                Stream.of("Aa12345!",
                        "p@ssw0Rd");
    }

    @ParameterizedTest
    @MethodSource("provideStringsForAbnormalPassword")
    @DisplayName("비정상적인 패스워드는 생성되지 않습니다.")
    void abnormalPassword(String input) {
        assertThrows(InvalidPasswordException.class, () -> new Password(input));
    }

    private static Stream<String> provideStringsForAbnormalPassword() {
        return
                Stream.of("aad",
                        "aabbccdd",
                        "abcd1234",
                        "!@#$%^&*",
                        "Aa123456");
    }

}
