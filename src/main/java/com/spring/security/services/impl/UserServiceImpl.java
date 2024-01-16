package com.spring.security.services.impl;

import com.spring.security.persistence.entities.User;
import com.spring.security.persistence.repositories.UserRepository;
import com.spring.security.services.OUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements OUserService {

    @Autowired
    UserRepository userRepository;


    @Override
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }
}

