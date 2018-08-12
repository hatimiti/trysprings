package com.github.hatimiti.spring.profiles;

import com.github.hatimiti.spring.common.db.entity.User;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;

@Profile("!dev")
@Repository
public class UserRepositoryImpl implements UserRepository {

    @Override
    public List<User> findAll() {
        User u = new User();
        u.userId = 123L;
        u.name = "本番ユーザー";
        return Arrays.asList(u);
    }
}