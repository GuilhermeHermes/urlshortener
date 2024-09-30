package com.guilhermehermes.urlshortener.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;


@Document(collection = "urls")
public class UrlEntity {

        @Id
        private String id;

        private String originalUrl;

        @Indexed(expireAfterSeconds = 0)
        private LocalDateTime expireAt;

        public UrlEntity() {
        }

        public UrlEntity(String id, String originalUrl, LocalDateTime expireAt) {
            this.id = id;
            this.originalUrl = originalUrl;
            this.expireAt = expireAt;
        }

        public String getId() {
            return id;
        }

        public String getOriginalUrl() {
            return originalUrl;
        }


        public void setId(String id) {
            this.id = id;
        }

        public void setOriginalUrl(String originalUrl) {
            this.originalUrl = originalUrl;
        }


}
