package com.url.shortener.domain;

import java.time.LocalDateTime;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

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
@Table(name = "url")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
@DynamicInsert
public class Url {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(length = 1000, nullable = false)
    private String originalUrl;

    @Column(length = 50)
    private String shortUrl;

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
        this.originalUrl = originalUrl;
    }

    public static Url from(String originalUrl) {
        return new Url(originalUrl);
    }
}
