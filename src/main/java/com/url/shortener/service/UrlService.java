package com.url.shortener.service;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.url.shortener.domain.Url;
import com.url.shortener.dto.request.UrlRequest;
import com.url.shortener.dto.response.UrlIdResponse;
import com.url.shortener.repository.UrlRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UrlService {

    private final UrlRepository urlRepository;

    @Transactional
    public UrlIdResponse createShortUrl(UrlRequest urlRequest) {
        Url url = urlRequest.toEntity();
        Optional<Url> foundUrl = urlRepository.findByOriginalUrl(url.getOriginalUrl());

        if (foundUrl.isPresent()) {
            Url existedUrl = foundUrl.get();
            existedUrl.increaseShorteningCount();

            return UrlIdResponse.from(existedUrl.getId());
        }

        Url savedUrl = urlRepository.save(url);
        savedUrl.generateShortUrlKey();

        return UrlIdResponse.from(savedUrl.getId());
    }
}