package com.videostream.app.service;

import com.videostream.app.entities.UserEntity;
import com.videostream.app.dao.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class CustomUserDetailService implements UserDetailsService {
    @Autowired
    private UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = userRepo.findByUsername(username);
        if(userEntity != null) {
           return new User(userEntity.getUsername(), userEntity.getPassword(), new ArrayList<>());
        }else{
            throw new UsernameNotFoundException("User Not Found");
        }
    }
}
