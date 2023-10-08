package com.url.shortener.dto.request;

import com.url.shortener.domain.Url;

import jakarta.validation.constraints.Pattern;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(staticName = "from")
public class UrlRequest {

    @Pattern(
        regexp = "^(http:\\/\\/www\\.|https:\\/\\/www\\.|http:\\/\\/|https:\\/\\/)?[a-z0-9]+([\\-\\.]{1}[a-z0-9]+)*\\.[a-z]{2,5}(:[0-9]{1,5})?(\\/.*)?$",
        message = "잘못된 형식의 Original Url이 입력됨"
    )
    private String originalUrl;

    public Url toEntity() {
        return Url.from(originalUrl);
    }
}
