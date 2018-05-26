package com.github.hatimiti.spring.di;

import com.github.hatimiti.spring.common.db.entity.User;

import java.util.List;

public interface DIRepository {
    List<User> findAllUsers();
}
