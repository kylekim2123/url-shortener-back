package com.url.shortener.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(staticName = "from")
public class ShortUrlKeyResponse {

    private String shortUrlKey;
}
