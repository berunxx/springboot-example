package com.myluffy.entity;

import javax.persistence.*;

@Entity
@Table(name = "short_url")
public class ShortUrl {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    // 短链字符串
    private String shortKey;
    // 原来域名
    private String originalUrl;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getShortKey() {
        return shortKey;
    }

    public void setShortKey(String shortKey) {
        this.shortKey = shortKey;
    }

    public String getOriginalUrl() {
        return originalUrl;
    }

    public void setOriginalUrl(String originalUrl) {
        this.originalUrl = originalUrl;
    }
}
