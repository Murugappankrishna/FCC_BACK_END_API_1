package com.murugappan.repo;

import com.murugappan.model.Url;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UrlRepository extends MongoRepository<Url, String> {
    Url findByShortUrl(int shortUrl);
}
