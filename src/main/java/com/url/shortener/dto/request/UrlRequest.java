package com.url.shortener.dto.request;

import com.url.shortener.domain.Url;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UrlRequest {

    private String originalUrl;

    public Url toEntity() {
        return Url.from(originalUrl);
    }
}
