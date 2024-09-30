package com.guilhermehermes.urlshortener.service;

import com.guilhermehermes.urlshortener.dto.ShortenUrlRequest;
import com.guilhermehermes.urlshortener.entities.UrlEntity;
import com.guilhermehermes.urlshortener.repository.UrlRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UrlService {

    UrlRepository urlRepository;

    public UrlService(UrlRepository urlRepository) {
        this.urlRepository = urlRepository;
    }


    public String shortenUrl(ShortenUrlRequest url) {
        String id;
        do {
            id = RandomStringUtils.randomAlphanumeric(5,10);
        } while (urlRepository.existsById(id));
        urlRepository.save(new UrlEntity(id, url.url(), LocalDateTime.now().plusMinutes(5)));

        return id;
    }

    public String getOriginalUrl(String id){
        return urlRepository.findById(id).orElseThrow().getOriginalUrl();
    }


}
