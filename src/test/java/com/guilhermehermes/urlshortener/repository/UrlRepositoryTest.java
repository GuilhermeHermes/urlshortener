package com.guilhermehermes.urlshortener.repository;

import com.guilhermehermes.urlshortener.entities.UrlEntity;
import net.bytebuddy.utility.dispatcher.JavaDispatcher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataMongoTest
@Testcontainers
class UrlRepositoryTest{

    @Container
    public static GenericContainer mongoDBContainer = new GenericContainer("mongo:latest")
            .withExposedPorts(27017)
            .withEnv("MONGO_INITDB_ROOT_USERNAME", "admin")
            .withEnv("MONGO_INITDB_ROOT_PASSWORD", "123");

    @Autowired
    private UrlRepository urlRepository;

    @DynamicPropertySource
    static void setProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.data.mongodb.uri", () ->
                String.format("mongodb://admin:123@%s:%d/test?authSource=admin",
                        mongoDBContainer.getHost(),
                        mongoDBContainer.getFirstMappedPort())
        );
    }

    @BeforeEach
    void setUp() {
        urlRepository.deleteAll();
    }



    @Test
    void shouldSaveAndRetrieveUrl() {
        // Given
        UrlEntity urlEntity = new UrlEntity();
        urlEntity.setOriginalUrl("https://www.example.com");
        urlEntity.setId("shortCode");
        // When
        UrlEntity savedEntity = urlRepository.save(urlEntity);

        // Then
        Optional<UrlEntity> foundEntity = urlRepository.findById(savedEntity.getId());
        assertThat(foundEntity).isPresent();
        assertThat(foundEntity.get().getId()).isEqualTo(savedEntity.getId());
        assertThat(foundEntity.get().getOriginalUrl()).isEqualTo("https://www.example.com");
    }

//    @Test
//    void shouldUpdateUrl() {
//        // Given
//        UrlEntity urlEntity = new UrlEntity("shortCode", "https://www.example.com");
//        UrlEntity savedEntity = urlRepository.save(urlEntity);
//
//        // When
//        savedEntity.setOriginalUrl("https://www.updated-example.com");
//        UrlEntity updatedEntity = urlRepository.save(savedEntity);
//
//        // Then
//        Optional<UrlEntity> foundEntity = urlRepository.findById(updatedEntity.getId());
//        assertThat(foundEntity).isPresent();
//        assertThat(foundEntity.get().getOriginalUrl()).isEqualTo("https://www.updated-example.com");
//    }
}