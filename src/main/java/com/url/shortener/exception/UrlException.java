package com.url.shortener.exception;

public class UrlException extends BusinessException {

    public UrlException(ExceptionRule exceptionRule, Object... rejectedValues) {
        super(exceptionRule, rejectedValues);
    }
}
