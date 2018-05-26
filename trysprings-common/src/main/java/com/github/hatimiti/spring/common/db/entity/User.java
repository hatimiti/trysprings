package com.github.hatimiti.spring.common.db.entity;

import org.springframework.core.style.ToStringCreator;

import java.io.Serializable;

public class User implements Serializable {

    public Long userId;

    public String name;

    public String password;

    @Override
    public String toString() {
        return new ToStringCreator(this)
                .append("userId", userId)
                .append("name", name)
                .append("password", password)
                .toString();
    }
}
