package com.url.shortener.dto.request;

import static org.assertj.core.api.Assertions.*;

import java.util.Set;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

class UrlRequestTest {

    @ParameterizedTest
    @ValueSource(strings = {"", " ", "https://google", "htps://google.co.kr", "https://.co.kr"})
    @DisplayName("URL 요청 DTO를 생성할 때, 잘못된 형식의 URL을 입력하면 예외 집합이 발생한다.")
    void 잘못된_형식_URL_예외_테스트(String inValidOriginalUrl) {
        //given
        UrlRequest urlRequest = UrlRequest.from(inValidOriginalUrl);

        //when
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        //then
        Set<ConstraintViolation<UrlRequest>> violationSet = validator.validate(urlRequest);
        assertThat(violationSet).isNotEmpty();
    }
}