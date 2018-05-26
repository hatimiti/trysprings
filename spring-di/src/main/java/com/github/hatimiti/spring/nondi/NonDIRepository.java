package com.github.hatimiti.spring.nondi;

import com.github.hatimiti.spring.common.db.entity.User;

import java.util.List;

public interface NonDIRepository {
    List<User> findAllUsers();
}
