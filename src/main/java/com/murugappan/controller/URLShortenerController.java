package com.murugappan.controller;


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
    ResponseEntity<?> shortUrl(@RequestParam("url") String url) {
        System.out.println("Vanakam Da Mapla in shorturl Controller");
        return urlService.shortUrl(url);
    }

    @GetMapping("/shorturl/{shortUrl}")
    public ResponseEntity<?> redirectToOriginalUrl(@PathVariable int shortUrl) {
        System.out.println("Vanakam Da  in shorturl Controller");
        return urlService.getOriginalUrl(shortUrl);
    }
}
