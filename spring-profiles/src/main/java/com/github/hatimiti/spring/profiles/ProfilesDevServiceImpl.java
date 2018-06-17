package com.github.hatimiti.spring.profiles;

import com.github.hatimiti.spring.common.db.entity.User;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Profile("dev")
@Service
public class ProfilesDevServiceImpl implements ProfilesService {

    private final UserRepository userRepository;

    public ProfilesDevServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public String hello() {
        return "default-service: "
                + userRepository.findAll()
                .stream().map(User::toString).collect(Collectors.joining());
    }
}