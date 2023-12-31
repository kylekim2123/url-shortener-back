package com.url.shortener.domain;

import static com.url.shortener.exception.ExceptionRule.*;

import java.time.LocalDateTime;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.url.shortener.exception.UrlException;
import com.url.shortener.utils.UrlConnectionUtil;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@DynamicInsert
@DynamicUpdate
@Table(name = "url")
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Url {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 1000, nullable = false)
    private String originalUrl;

    @Column(length = 10, unique = true)
    private String shortUrlKey;

    @ColumnDefault("0")
    private Integer requestCount;

    @ColumnDefault("1")
    private Integer shorteningCount;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdDatetime;

    @LastModifiedDate
    private LocalDateTime updatedDatetime;

    private Url(String originalUrl) {
        validateOriginalUrlIsValid(originalUrl);

        this.originalUrl = originalUrl;
    }

    public static Url from(String originalUrl) {
        return new Url(originalUrl);
    }

    public void updateShortUrlKey(String shortUrlKey) {
        this.shortUrlKey = shortUrlKey;
    }

    public void increaseRequestCount() {
        this.requestCount += 1;
    }

    public void increaseShorteningCount() {
        this.shorteningCount += 1;
    }

    private void validateOriginalUrlIsValid(String originalUrl) {
        if (!UrlConnectionUtil.isValidUrl(originalUrl)) {
            throw new UrlException(ORIGINAL_URL_INVALID, originalUrl);
        }
    }
}
