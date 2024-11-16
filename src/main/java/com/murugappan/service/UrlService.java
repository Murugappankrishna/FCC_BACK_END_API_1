package com.murugappan.service;

import com.murugappan.model.Url;
import com.murugappan.repo.UrlRepository;
import lombok.AllArgsConstructor;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URI;
import java.net.URL;
import java.util.List;

@Service
@AllArgsConstructor
public class UrlService {
    private final UrlRepository urlRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    private static boolean validate(String url) {
        try {
            // Step 1: Validate the URL format using URI
            URI uri = new URI(url);
            if (!uri.isAbsolute() || uri.getHost() == null) {
                System.out.println("Invalid URL format.");
                return false;
            }
            URL parsedUrl = uri.toURL();
            String hostname = parsedUrl.getHost();
            InetAddress address = InetAddress.getByName(hostname);
            System.out.println("Resolved Address: " + address);
            return true;
        } catch (Exception e) {
            // Handle invalid URLs and resolution failures
            System.out.println("Error: " + e.getMessage());
            return false;
        }
    }

    public ResponseEntity<?> shortUrl(String url) {
        if (validate(url)) {
            List<Url> allUrls = urlRepository.findAll();
            int maxShortUrl = allUrls.stream()
                    .mapToInt(Url::getShortUrl)
                    .max()
                    .orElse(0);
            int newShortUrl = maxShortUrl + 1;
            Url newUrl = new Url(url, newShortUrl);
            urlRepository.save(newUrl);
            Document responseJson = new Document();
            responseJson.put("original_url", newUrl.getOriginalUrl());
            responseJson.put("short_url", newUrl.getShortUrl());
            return ResponseEntity.ok(responseJson);
        } else {
            Document jsonObject = new Document();
            jsonObject.put("error", "invalid url");
            return ResponseEntity.ok(jsonObject);
        }
    }

    public String getOriginalUrl(int shortUrl) {
        Url url = urlRepository.findByShortUrl(shortUrl);
        if (url != null) {
           return url.getOriginalUrl();
        } else {
            return null;
        }
    }
}
