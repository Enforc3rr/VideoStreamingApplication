package com.videostream.app.repository;

import com.videostream.app.entities.FileEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface FileRepo extends MongoRepository<FileEntity , String> {
}
