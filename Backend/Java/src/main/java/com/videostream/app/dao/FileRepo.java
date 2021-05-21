package com.videostream.app.dao;

import com.videostream.app.entities.FileEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface FileRepo extends MongoRepository<FileEntity , String> {
    void deleteByFileName(String fileName);
}
