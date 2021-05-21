package com.videostream.app.dao;

import com.videostream.app.entities.UserEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends MongoRepository<UserEntity,String> {
    UserEntity findByUsername(String username);
}
