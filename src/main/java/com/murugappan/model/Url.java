package com.murugappan.model;

import com.fasterxml.jackson.annotation.JsonProperty;
//import jakarta.persistence.Entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.stereotype.Component;



@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "URL_TO_SHORTURL")
public class Url {
    @Id
    private String id;
@Field("original_url")
    private String OriginalUrl;
@Field("short_url")
    private int ShortUrl;

    public Url(String originalUrl, int shortUrl) {
        this.OriginalUrl = originalUrl;
        this.ShortUrl = shortUrl;
    }
}

