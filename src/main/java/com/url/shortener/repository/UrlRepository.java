package com.url.shortener.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.url.shortener.domain.Url;

public interface UrlRepository extends JpaRepository<Url, Long> {

    Optional<Url> findByOriginalUrl(String originalUrl);
}
