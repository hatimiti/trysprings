package com.github.hatimiti.spring.rest.impl;

import com.github.hatimiti.spring.common.db.entity.User;
import com.github.hatimiti.spring.rest.UserRepository;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class UserRepositoryImpl implements UserRepository {

    private Map<Long, User> users;

    public UserRepositoryImpl() {
        users = new HashMap<>();
    }

    @Override
    public List<User> findAll() {
        return Collections.unmodifiableList(
                Arrays.asList(users.values().toArray(new User[users.size()])));
    }

    @Override
    public Optional<User> findById(long id) {
        return Optional.ofNullable(users.get(id));
    }

    @Override
    public User insert(User user) {
        users.put(user.userId, user);
        return user;
    }

    @Override
    public User update(User user) {
        return insert(user);
    }

    @Override
    public User delete(long id) {
        return users.remove(id);
    }

    @Override
    public List<User> deleteAll() {
        final List<User> all = findAll();
        users.clear();
        return all;
    }
}
