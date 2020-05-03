package dev.smjeon.commerce.user.domain;

import dev.smjeon.commerce.user.exception.InvalidEmailException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class EmailTest {
    @ParameterizedTest
    @MethodSource("provideStringsForNormalEmail")
    @DisplayName("정상적으로 이메일이 생성됩니다.")
    void normalEmail(String input) {
        assertDoesNotThrow(() -> new Email(input));
    }

    private static Stream<String> provideStringsForNormalEmail() {
        return
                Stream.of("oeeen3@gmail.com",
                        "martin-fowler@gmail.com",
                        "smjeon@naver.com",
                        "sm.jeon@good.com",
                        "smjeon@good-email.com");
    }

    @ParameterizedTest
    @MethodSource("provideStringsForAbnormalEmail")
    @DisplayName("비정상적인 이메일이면 생성되지 않습니다.")
    void abnormalEmail(String input) {
        assertThrows(InvalidEmailException.class, () -> new Email(input));
    }

    private static Stream<String> provideStringsForAbnormalEmail() {
        return
                Stream.of("aaa.bbb.com",
                        "aaa.com",
                        "aaaaaaa",
                        "test.test",
                        "abc--aa@abc.com",
                        "abc.abc@abc--a.com");
    }
}
