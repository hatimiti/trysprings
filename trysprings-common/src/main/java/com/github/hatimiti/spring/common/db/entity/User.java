package com.github.hatimiti.spring.common.db.entity;

import org.springframework.core.style.ToStringCreator;

import java.io.Serializable;
import java.util.Objects;

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

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        if (obj.getClass() != User.class) {
            return false;
        }
        return this.userId == ((User) obj).userId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId);
    }
}
