package com.github.hatimiti.spring.rest;

import com.github.hatimiti.spring.common.db.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    public List<User> findAll();
    public Optional<User> findById(long id);
    public User insert(User user);
    public User update(User user);
    public User delete(long id);
    public List<User> deleteAll();
}
