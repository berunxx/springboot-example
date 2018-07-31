package com.myluffy.dao;

import com.myluffy.entity.ShortUrl;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShortRepository extends JpaRepository<ShortUrl, Long>{
    ShortUrl getByShortKey(String shortKey);

    ShortUrl getByOriginalUrl(String originalUrl);
}
