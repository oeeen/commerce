package dev.smjeon.commerce.user.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class NickNameTest {
    @ParameterizedTest
    @MethodSource("provideStringsForNormalNickName")
    @DisplayName("정상적으로 닉네임이 생성됩니다.")
    void normalNickName(String input) {
        assertDoesNotThrow(() -> new NickName(input));
    }

    private static Stream<String> provideStringsForNormalNickName() {
        return
                Stream.of("Martin",
                        "Seongmo.Jeon",
                        "Seongmo-Jeon",
                        "Hi-Im-Seongmo");
    }
}
