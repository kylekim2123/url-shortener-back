package com.url.shortener.service;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.url.shortener.dto.request.UrlRequest;
import com.url.shortener.dto.response.UrlIdResponse;

@SpringBootTest
class UrlServiceTest {

    @Autowired
    private UrlService urlService;

    @Test
    @Transactional
    @DisplayName("임의의 URL의 단축 정보를 생성하여 저장된 short URL의 ID를 반환한다.")
    void 단축_URL_생성_여부_테스트() {
        UrlRequest urlRequest = UrlRequest.from("https://www.naver.com/");
        UrlIdResponse urlIdResponse = urlService.createShortUrl(urlRequest);

        assertThat(urlIdResponse.getId()).isEqualTo(1L);
    }
}