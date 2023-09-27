package com.url.shortener.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.url.shortener.domain.Url;

public interface UrlRepository extends JpaRepository<Url, Long> {

}
