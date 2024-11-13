package com.murugappan.controller;

import com.murugappan.model.InputUrl;
import com.murugappan.model.Url;
import com.murugappan.service.UrlService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@CrossOrigin


public class URLShortenerController {
    private final UrlService urlService;

    URLShortenerController(UrlService urlService) {
        this.urlService = urlService;
    }

    @PostMapping("/shorturl")
    ResponseEntity<?> shortUrl(@RequestBody InputUrl url) {
        System.out.println("Vanakam Da Mapla in shorturl Controller");
        return urlService.shortUrl(url.getOriginalUrl());
    }

    @GetMapping("/shorturl/{shortUrl}")
    public ResponseEntity<?> redirectToOriginalUrl(@PathVariable int shortUrl) {
        return urlService.getOriginalUrl(shortUrl);
    }
}
