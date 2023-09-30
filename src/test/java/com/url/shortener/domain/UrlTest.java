package com.url.shortener.domain;

import static com.url.shortener.exception.ExceptionRule.*;
import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import com.url.shortener.exception.UrlException;

class UrlTest {

    @ParameterizedTest
    @DisplayName("올바르지 않은 형식의 URL 주소를 이용해 url 객체를 만들 때, 예외가 발생한다.")
    @ValueSource(strings = {"", " ", "abcde", "https://www.google", "https://www.ldskfjiwlenvbl.com/"})
    void 올바르지_않은_URL_객체_생성_예외_테스트(String inValidOriginalUrl) {
        //when, then
        assertThatThrownBy(() -> Url.from(inValidOriginalUrl))
            .isInstanceOf(UrlException.class)
            .hasMessage(ORIGINAL_URL_INVALID.getMessage());
    }
}