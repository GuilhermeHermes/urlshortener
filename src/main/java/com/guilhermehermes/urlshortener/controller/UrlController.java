package com.guilhermehermes.urlshortener.controller;


import com.guilhermehermes.urlshortener.dto.ShortenUrlRequest;
import com.guilhermehermes.urlshortener.dto.ShortenUrlResponse;
import com.guilhermehermes.urlshortener.entities.UrlEntity;
import com.guilhermehermes.urlshortener.repository.UrlRepository;
import com.guilhermehermes.urlshortener.service.UrlService;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/url")
public class UrlController {

    UrlService urlService;

    public UrlController(UrlService urlService) {
        this.urlService = urlService;
    }

    @PostMapping(value = "/shorten-url")
    public ResponseEntity<ShortenUrlResponse> shortenUrl(@RequestBody ShortenUrlRequest url, HttpServletRequest request) {

        String id = urlService.shortenUrl(url);


        var redirectUrl = request.getRequestURL().toString().replace("shorten-url", id);

        return ResponseEntity.ok(new ShortenUrlResponse(redirectUrl));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Void> redirect(@PathVariable String id){
        String originalUrl = urlService.getOriginalUrl(id);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create(originalUrl));

        return ResponseEntity.status(HttpStatus.FOUND).headers(headers).build();
    }
}
