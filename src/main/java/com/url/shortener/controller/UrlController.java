package com.url.shortener.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.url.shortener.dto.request.UrlRequest;
import com.url.shortener.dto.response.ShortUrlKeyResponse;
import com.url.shortener.dto.response.UrlResponse;
import com.url.shortener.service.UrlService;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class UrlController {

    private final UrlService urlService;

    @PostMapping("/api/short-urls")
    @ResponseStatus(HttpStatus.CREATED)
    public ShortUrlKeyResponse createShortUrl(@Valid @RequestBody UrlRequest urlRequest) {
        return urlService.createShortUrl(urlRequest);
    }

    @GetMapping("/api/short-urls/{key}")
    public UrlResponse findShortUrlById(@PathVariable String key) {
        return urlService.findShortUrlById(key);
    }

    @GetMapping("/{key}")
    @ResponseStatus(HttpStatus.MOVED_PERMANENTLY)
    public void requestShortUrl(@PathVariable String key, HttpServletResponse response) {
        String originalUrl = urlService.getOriginalUrlByShortUrlKey(key);
        response.setHeader("Location", originalUrl);
        response.setHeader("Cache-Control", "private, max-age=90");
    }
}
