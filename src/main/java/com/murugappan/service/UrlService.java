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
            URL inputUrl = new URL(url); // Parse the URL
            HttpURLConnection connection = (HttpURLConnection) inputUrl.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000); // Set timeout (optional)
            connection.setReadTimeout(5000); // Set read timeout (optional)

            int responseCode = connection.getResponseCode(); // Get the response code

            if (responseCode == 200) {
                // Return "OK" if the response code is 200
                return true;
            } else {
                // Return "NO" for other response codes
                return false;
            }
        } catch (IOException e) {
            // Return "NO" if any exception occurs (e.g., connection issues)
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
            return ResponseEntity.badRequest().body(jsonObject);
        }
    }

    public ResponseEntity<?> getOriginalUrl(int shortUrl) {
        Url url = urlRepository.findByShortUrl(shortUrl);
        if (url != null) {
            return ResponseEntity.status(HttpStatus.FOUND).header("Location", url.getOriginalUrl()).build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
