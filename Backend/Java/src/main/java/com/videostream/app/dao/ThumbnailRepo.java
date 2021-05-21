package com.videostream.app.dao;

import com.videostream.app.entities.ThumbnailEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ThumbnailRepo extends MongoRepository<ThumbnailEntity,String> {
    ThumbnailEntity findThumbnailEntityByVideoID(String thumbnailName);
    void deleteByVideoID(String videoId);
}
