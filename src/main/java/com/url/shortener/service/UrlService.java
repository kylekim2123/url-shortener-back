package com.url.shortener.service;

import static com.url.shortener.exception.ExceptionRule.*;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.url.shortener.domain.Url;
import com.url.shortener.dto.request.UrlRequest;
import com.url.shortener.dto.response.UrlIdResponse;
import com.url.shortener.dto.response.UrlResponse;
import com.url.shortener.exception.UrlException;
import com.url.shortener.repository.UrlRepository;
import com.url.shortener.utils.encoding.EncodingUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UrlService {

    private static final String EMPTY_STRING = "";

    private final UrlRepository urlRepository;
    private final EncodingUtil encodingUtil;

    @Value("${env.server-base-url}")
    private String serverBaseUrl;

    @Transactional
    public UrlIdResponse createShortUrl(UrlRequest urlRequest) {
        validateOriginalUrlIsShortUrl(urlRequest.getOriginalUrl());

        Url url = urlRequest.toEntity();
        Optional<Url> foundUrl = urlRepository.findByOriginalUrl(url.getOriginalUrl());

        if (foundUrl.isPresent()) {
            Url existedUrl = foundUrl.get();
            existedUrl.increaseShorteningCount();

            return UrlIdResponse.from(existedUrl.getId());
        }

        Url savedUrl = urlRepository.save(url);
        String shortUrlKey = encodingUtil.encodeFromNumber(savedUrl.getId());

        savedUrl.updateShortUrlKey(shortUrlKey);

        return UrlIdResponse.from(savedUrl.getId());
    }

    public UrlResponse findShortUrlById(Long id) {
        Url url = urlRepository.findById(id)
            .orElseThrow(() -> new UrlException(SHORT_URL_NOT_EXISTED, id));

        String fullShortUrlAddress = serverBaseUrl + url.getShortUrlKey();

        return UrlResponse.fromEntity(url, fullShortUrlAddress);
    }

    @Transactional
    public String getOriginalUrlByShortUrlKey(String key) {
        Url url = urlRepository.findByShortUrlKey(key)
            .orElseThrow(() -> new UrlException(SHORT_URL_KEY_NOT_EXISTED, key));

        url.increaseRequestCount();

        return url.getOriginalUrl();
    }

    private void validateOriginalUrlIsShortUrl(String originalUrl) {
        if (originalUrl.startsWith(serverBaseUrl)) {
            String key = originalUrl.replaceAll(serverBaseUrl, EMPTY_STRING);

            if (urlRepository.existsByShortUrlKey(key)) {
                throw new UrlException(SHORT_URL_CANNOT_BE_SHORTENED, originalUrl);
            }
        }
    }
}
