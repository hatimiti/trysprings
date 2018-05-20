package com.github.hatimiti.spring.data.jpa.db.repository;

import com.github.hatimiti.spring.data.jpa.db.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}
