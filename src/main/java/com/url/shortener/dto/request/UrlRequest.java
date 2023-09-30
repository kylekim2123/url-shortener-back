package com.url.shortener.dto.request;

import com.url.shortener.domain.Url;

import jakarta.validation.constraints.NotEmpty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(staticName = "from")
public class UrlRequest {

    @NotEmpty(message = "Original Url이 입력되지 않음")
    private String originalUrl;

    public Url toEntity() {
        return Url.from(originalUrl);
    }
}
