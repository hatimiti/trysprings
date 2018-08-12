package com.github.hatimiti.spring.profiles.impl;

import com.github.hatimiti.spring.common.db.entity.User;
import com.github.hatimiti.spring.profiles.UserRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;

@Profile("dev")
@Repository
public class UserDevRepositoryImpl implements UserRepository {

    @Override
    public List<User> findAll() {
        User u = new User();
        u.userId = 123L;
        u.name = "開発ユーザー";
        return Arrays.asList(u);
    }
}