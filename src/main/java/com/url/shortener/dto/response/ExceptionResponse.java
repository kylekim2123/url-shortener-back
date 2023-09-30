package com.url.shortener.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ExceptionResponse {

    private final String requestUrl;
    private final String message;
    private final Object[] rejectedValues;

    @Builder
    private ExceptionResponse(String requestUrl, String message, Object[] rejectedValues) {
        this.requestUrl = requestUrl;
        this.message = message;
        this.rejectedValues = rejectedValues;
    }
}
