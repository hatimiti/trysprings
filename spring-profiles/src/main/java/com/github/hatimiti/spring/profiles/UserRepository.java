package com.github.hatimiti.spring.profiles;

import com.github.hatimiti.spring.common.db.entity.User;

import java.util.List;

public interface UserRepository {
    List<User> findAll();
}