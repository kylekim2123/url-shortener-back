package com.url.shortener.utils;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class Base62UtilTest {

    @ParameterizedTest
    @DisplayName("숫자가 Base62 알고리즘을 거쳐 정상적으로 임의의 문자열로 변환된다.")
    @CsvSource(value = {"1,1", "1000001,39C4", "232442556,UvIjF"})
    void BASE62_정상_변환_테스트(Long number, String expectedEncodedKey) {
        String actualEncodedKey = Base62Util.encodeFromNumber(number);

        assertThat(actualEncodedKey).isEqualTo(expectedEncodedKey);
    }
}