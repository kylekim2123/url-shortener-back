package com.url.shortener.service;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.url.shortener.dto.request.UrlRequest;
import com.url.shortener.dto.response.UrlIdResponse;
import com.url.shortener.repository.UrlRepository;

@SpringBootTest
class UrlServiceTest {

    @Autowired
    private UrlService urlService;

    @Autowired
    private UrlRepository urlRepository;

    @AfterEach
    void deleteAll() {
        urlRepository.deleteAll();
    }

    @Test
    @DisplayName("임의의 URL의 단축 정보를 생성하여 저장된 short URL의 ID를 반환한다.")
    void 단축_URL_생성_여부_테스트() {
        UrlRequest urlRequest = UrlRequest.from("https://www.naver.com/");
        UrlIdResponse urlIdResponse = urlService.createShortUrl(urlRequest);

        assertThat(urlIdResponse.getId()).isEqualTo(1L);
    }

    @Test
    @DisplayName("이미 존재하는 URL의 단축 정보를 생성할 때, 새로 생성하지 않고 기존 short URL의 ID를 반환한다.")
    void 단축_URL_중복_생성_여부_테스트() {
        UrlRequest urlRequest1 = UrlRequest.from("https://www.naver.com/");
        UrlIdResponse urlIdResponse1 = urlService.createShortUrl(urlRequest1);

        UrlRequest urlRequest2 = UrlRequest.from("https://www.naver.com/");
        UrlIdResponse urlIdResponse2 = urlService.createShortUrl(urlRequest2);

        assertThat(urlIdResponse2.getId()).isEqualTo(urlIdResponse1.getId());
    }
}