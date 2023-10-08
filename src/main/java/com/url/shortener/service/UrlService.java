package com.url.shortener.service;

import static com.url.shortener.exception.ExceptionRule.*;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.url.shortener.domain.Url;
import com.url.shortener.dto.request.UrlRequest;
import com.url.shortener.dto.response.ShortUrlKeyResponse;
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
    public ShortUrlKeyResponse createShortUrl(UrlRequest urlRequest) {
        validateOriginalUrlIsShortUrl(urlRequest.getOriginalUrl());

        Url url = urlRequest.toEntity();
        Optional<Url> foundUrl = urlRepository.findByOriginalUrl(url.getOriginalUrl());

        if (foundUrl.isPresent()) {
            Url existedUrl = foundUrl.get();
            existedUrl.increaseShorteningCount();

            return ShortUrlKeyResponse.from(existedUrl.getShortUrlKey());
        }

        Url savedUrl = urlRepository.save(url);
        String shortUrlKey = encodingUtil.encodeFromNumber(savedUrl.getId());

        savedUrl.updateShortUrlKey(shortUrlKey);

        return ShortUrlKeyResponse.from(savedUrl.getShortUrlKey());
    }

    private void validateOriginalUrlIsShortUrl(String originalUrl) {
        if (originalUrl.startsWith(serverBaseUrl)) {
            String key = originalUrl.replaceAll(serverBaseUrl, EMPTY_STRING);

            if (urlRepository.existsByShortUrlKey(key)) {
                throw new UrlException(SHORT_URL_CANNOT_BE_SHORTENED, originalUrl);
            }
        }
    }

    public UrlResponse findShortUrlById(String key) {
        Url url = findShortUrl(key);
        String fullShortUrlAddress = serverBaseUrl + url.getShortUrlKey();

        return UrlResponse.fromEntity(url, fullShortUrlAddress);
    }

    @Transactional
    public String getOriginalUrlByShortUrlKey(String key) {
        Url url = findShortUrl(key);
        url.increaseRequestCount();

        return url.getOriginalUrl();
    }

    private Url findShortUrl(String key) {
        return urlRepository.findByShortUrlKey(key)
            .orElseThrow(() -> new UrlException(SHORT_URL_KEY_NOT_EXISTED, key));
    }
}
