package com.guilhermehermes.urlshortener.repository;

import com.guilhermehermes.urlshortener.entities.UrlEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UrlRepository extends MongoRepository<UrlEntity, String> {

}
