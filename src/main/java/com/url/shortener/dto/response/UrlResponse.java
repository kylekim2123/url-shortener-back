package com.url.shortener.dto.response;

import java.time.LocalDateTime;

import com.url.shortener.domain.Url;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class UrlResponse {

    private Long id;
    private String originalUrl;
    private String shortUrl;
    private Integer requestCount;
    private Integer shorteningCount;
    private LocalDateTime createdDatetime;
    private LocalDateTime updatedDatetime;

    public static UrlResponse fromEntity(Url url, String fullShortUrlAddress) {
        return UrlResponse.builder()
            .id(url.getId())
            .originalUrl(url.getOriginalUrl())
            .shortUrl(fullShortUrlAddress)
            .requestCount(url.getRequestCount())
            .shorteningCount(url.getShorteningCount())
            .createdDatetime(url.getCreatedDatetime())
            .updatedDatetime(url.getUpdatedDatetime())
            .build();
    }
}
