package com.github.hatimiti.spring.di;

import com.github.hatimiti.spring.common.db.entity.User;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;

@Repository
public class DIRepositoryImpl implements DIRepository {
    @Override
    public List<User> findAllUsers() {
        return Arrays.asList(
                createUser(1L),
                createUser(2L),
                createUser(3L),
                createUser(4L),
                createUser(5L),
                createUser(6L),
                createUser(7L)
        );
    }

    private User createUser(final long userId) {
        final User u = new User();
        u.userId = userId;
        u.name = "Sample1";
        u.password = "password";
        return u;
    }
}
