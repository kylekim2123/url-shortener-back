package com.url.shortener.service;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.url.shortener.service.encoder.UrlEncoder;

@SpringBootTest
class UrlEncoderTest {

    @Autowired
    private UrlEncoder urlEncoder;

    @ParameterizedTest
    @DisplayName("숫자가 인코딩 알고리즘을 거쳐 정상적으로 임의의 문자열로 변환된다.")
    @CsvSource(value = {"1,39C4", "123456789,ZtCQ8"})
    void 인코딩_정상_변환_테스트(long number, String expectedEncodedKey) {
        //when
        String actualEncodedKey = urlEncoder.encodeFromNumber(number);

        //then
        assertThat(actualEncodedKey).isEqualTo(expectedEncodedKey);
    }
}