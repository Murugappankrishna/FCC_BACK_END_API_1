package com.murugappan.controller;


import com.murugappan.model.Url;
import com.murugappan.service.UrlService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin


public class URLShortenerController {
    private final UrlService urlService;

    URLShortenerController(UrlService urlService) {
        this.urlService = urlService;
    }

    @PostMapping("/api/shorturl")
    ResponseEntity<?> shortUrl(@RequestParam("url") String url) {
        System.out.println("shorturl Controller");
        return urlService.shortUrl(url);
    }

    @GetMapping("/api/shorturl/{shortUrl}")
    public ResponseEntity<?> redirectToOriginalUrl(@PathVariable int shortUrl, HttpServletResponse response) {
        System.out.println("GetOG URL");
        String targetUrl = urlService.getOriginalUrl(shortUrl);
        try {
            if (targetUrl != null) {
                response.sendRedirect(targetUrl);
                return null;
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Short URL not found");
            }
        }
        catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Internal Server Error");
        }
    }
    @GetMapping("/") // The path here matches the base URL path
    public String demo(@RequestParam(value = "v", required = false) String v) {
        System.out.println("/ mapping");
        return v != null ? v : "Parameter 'v' not provided";
    }
}
