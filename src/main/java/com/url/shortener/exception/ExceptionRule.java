package com.url.shortener.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ExceptionRule {

    ORIGINAL_URL_INVALID(HttpStatus.BAD_REQUEST, "올바르지 않은 Original URL 입력"),
    SHORT_URL_KEY_INVALID(HttpStatus.BAD_REQUEST, "올바르지 않은 Short URL Key 입력"),
    SHORT_URL_KEY_NOT_EXISTED(HttpStatus.NOT_FOUND, "입력한 Short URL Key를 찾을 수 없음"),
    SHORT_URL_CANNOT_BE_SHORTENED(HttpStatus.BAD_REQUEST, "Short URL은 더이상 단축할 수 없음"),

    BAD_REQUEST(HttpStatus.BAD_REQUEST, "사용자 입력 유효성 검사 실패"),
    NOT_FOUND(HttpStatus.NOT_FOUND, "요청한 URL에 해당하는 리소스를 찾을 수 없음"),
    METHOD_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED, "허용되지 않은 HTTP Method 요청 발생"),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "기타 서버 내부 에러 발생"),
    ;

    private final HttpStatus status;
    private final String message;
}
