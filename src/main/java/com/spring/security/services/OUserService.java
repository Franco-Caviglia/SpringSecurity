package com.spring.security.services;

import com.spring.security.persistence.entities.User;

import java.util.List;

public interface OUserService {

    public List<User> findAllUsers();

}
