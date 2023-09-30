package com.url.shortener.utils;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class UrlConnectionUtilTest {

    @ParameterizedTest
    @DisplayName("올바른 형식의 URL에 대해 true를 반환한다.")
    @ValueSource(strings = {"https://www.google.co.kr/", "https://www.naver.com/"})
    void 올바른_형식_URL_테스트(String validOriginalUrl) {
        //when
        boolean isValid = UrlConnectionUtil.isValidUrl(validOriginalUrl);

        //then
        assertThat(isValid).isTrue();
    }

    @ParameterizedTest
    @DisplayName("올바르지 않은 형식의 URL에 대해 false를 반환한다.")
    @ValueSource(strings = {"", " ", "abcde", "https://www.google", "https://www.ldskfjiwlenvbl.com/"})
    void 올바르지_않은_형식_URL_테스트(String inValidOriginalUrl) {
        //when
        boolean isValid = UrlConnectionUtil.isValidUrl(inValidOriginalUrl);

        //then
        assertThat(isValid).isFalse();
    }
}