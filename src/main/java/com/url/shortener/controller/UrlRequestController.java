package com.url.shortener.controller;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.url.shortener.service.UrlService;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class UrlRequestController {

    private final UrlService urlService;

    @GetMapping("/{key}")
    @ResponseStatus(HttpStatus.MOVED_PERMANENTLY)
    public void requestShortUrl(@PathVariable String key, HttpServletResponse response) {
        String originalUrl = urlService.getOriginalUrlByShortUrlKey(key);
        response.setHeader("Location", originalUrl);
        response.setHeader("Cache-Control", "private, max-age=90");
    }
}
