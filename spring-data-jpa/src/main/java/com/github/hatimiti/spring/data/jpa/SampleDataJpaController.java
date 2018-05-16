package com.github.hatimiti.spring.data.jpa;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.List;

@RestController
public class SampleDataJpaController {

    private final UserRepository userRepository;

    public SampleDataJpaController(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/users")
    public List<User> findUsers() {
        return this.userRepository.findAll();
    }

    @GetMapping("/adduser")
    public User addUser() {
        final User u = new User();
        long v = -1L;
        try {
            v = SecureRandom.getInstanceStrong().nextLong();
        } catch (NoSuchAlgorithmException e) {
            v = 0L;
        }
        u.name = "Dummy-" + v;
        u.password = u.name;
        u.enabled = "true";
        u.authority = "DUMMY";
        return this.userRepository.save(u);
    }
}
