package com.videostream.app.repository;

import com.videostream.app.entities.ThumbnailEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ThumbnailRepo extends MongoRepository<ThumbnailEntity,String> {
}
