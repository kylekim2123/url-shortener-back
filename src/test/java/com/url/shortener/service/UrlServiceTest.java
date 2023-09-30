package com.url.shortener.service;

import static com.url.shortener.exception.ExceptionRule.*;
import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import com.url.shortener.domain.Url;
import com.url.shortener.dto.request.UrlRequest;
import com.url.shortener.dto.response.UrlIdResponse;
import com.url.shortener.dto.response.UrlResponse;
import com.url.shortener.exception.UrlException;
import com.url.shortener.repository.UrlRepository;

@SpringBootTest
class UrlServiceTest {

    private static final String INIT_ORIGINAL_URL = "https://www.google.co.kr/";

    @Autowired
    private UrlService urlService;

    @Autowired
    private UrlRepository urlRepository;

    @Value("${env.server-base-url}")
    private String serverBaseUrl;

    @BeforeEach
    void initData() {
        //given
        UrlRequest urlRequest = UrlRequest.from(INIT_ORIGINAL_URL);
        urlService.createShortUrl(urlRequest);
    }

    @AfterEach
    void deleteAll() {
        urlRepository.deleteAll();
    }

    @Test
    @DisplayName("임의의 URL의 단축 정보를 생성하여 저장된 short URL의 ID를 반환한다.")
    void 단축_URL_생성_여부_테스트() {
        //when
        UrlRequest urlRequest = UrlRequest.from("https://www.naver.com/");
        UrlIdResponse urlIdResponse = urlService.createShortUrl(urlRequest);

        //then
        long allUrlsCount = urlRepository.count();
        assertThat(allUrlsCount).isEqualTo(2);
    }

    @Test
    @DisplayName("이미 존재하는 URL의 단축 정보를 생성할 때, 새로 생성하지 않고 기존 short URL의 ID를 반환한다.")
    void 단축_URL_중복_생성_여부_테스트() {
        //when
        UrlRequest urlRequest = UrlRequest.from(INIT_ORIGINAL_URL);
        UrlIdResponse urlIdResponse = urlService.createShortUrl(urlRequest);

        //then
        Url existedUrl = urlRepository.findByOriginalUrl(INIT_ORIGINAL_URL).get();
        assertThat(urlIdResponse.getId()).isEqualTo(existedUrl.getId());
        assertThat(existedUrl.getShorteningCount()).isEqualTo(2);
    }

    @Test
    @DisplayName("short URL의 id값을 기준으로 정상적으로 UrlResponse를 반환한다.")
    void ID값_기준_정상_조회_테스트() {
        //given
        Url savedUrl = urlRepository.findByOriginalUrl(INIT_ORIGINAL_URL).get();
        String key = savedUrl.getShortUrlKey();

        //when
        UrlResponse urlResponse = urlService.findShortUrlById(savedUrl.getId());

        //then
        assertThat(urlResponse.getId()).isEqualTo(savedUrl.getId());
        assertThat(urlResponse.getOriginalUrl()).isEqualTo(INIT_ORIGINAL_URL);
        assertThat(urlResponse.getShortUrl()).isEqualTo(serverBaseUrl + key);
    }

    @Test
    @DisplayName("short URL의 key값을 기준으로 정상적으로 Original Url을 반환한다.")
    void KEY값_기준_정상_ORIGINAL_URL_조회_테스트() {
        //given
        Url savedUrl = urlRepository.findByOriginalUrl(INIT_ORIGINAL_URL).get();
        String key = savedUrl.getShortUrlKey();

        //when
        String originalUrl = urlService.getOriginalUrlByShortUrlKey(key);

        //then
        assertThat(originalUrl).isEqualTo(INIT_ORIGINAL_URL);
    }

    @Test
    @DisplayName("short URL을 다시 단축하려는 경우 예외가 발생한다.")
    void SHORT_URL_재단축_예외_테스트() {
        //given
        Url savedUrl = urlRepository.findByOriginalUrl(INIT_ORIGINAL_URL).get();
        String shortUrl = serverBaseUrl + savedUrl.getShortUrlKey();
        UrlRequest urlRequest = UrlRequest.from(shortUrl);

        //when, then
        assertThatThrownBy(() -> urlService.createShortUrl(urlRequest))
            .isInstanceOf(UrlException.class)
            .hasMessage(SHORT_URL_CANNOT_BE_SHORTENED.getMessage());
    }

    @Test
    @DisplayName("short URL의 id값에 해당하는 정보가 없으면 예외가 발생한다.")
    void ID값_기준_조회_예외_테스트() {
        //given
        Long unexistedId = 0L;

        //when,then
        assertThatThrownBy(() -> urlService.findShortUrlById(unexistedId))
            .isInstanceOf(UrlException.class)
            .hasMessage(SHORT_URL_NOT_EXISTED.getMessage());
    }

    @Test
    @DisplayName("short URL의 Key값에 해당하는 정보가 없으면 예외가 발생한다.")
    void KEY값_기준_조회_예외_테스트() {
        //given
        String unexistedKey = "unexisted";

        //when,then
        assertThatThrownBy(() -> urlService.getOriginalUrlByShortUrlKey(unexistedKey))
            .isInstanceOf(UrlException.class)
            .hasMessage(SHORT_URL_KEY_NOT_EXISTED.getMessage());
    }
}