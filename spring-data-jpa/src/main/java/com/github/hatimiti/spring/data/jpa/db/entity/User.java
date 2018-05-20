package com.github.hatimiti.spring.data.jpa.db.entity;

import org.springframework.core.style.ToStringCreator;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "M_USER")
public class User implements Serializable {

    @Id
    @GeneratedValue
    @Column(name = "M_USER_ID")
    public Long userId;

    @Column(name = "NAME")
    public String name;

    @Column(name = "PASSWORD")
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
