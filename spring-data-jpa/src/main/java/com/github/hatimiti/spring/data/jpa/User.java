package com.github.hatimiti.spring.data.jpa;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "M_USER")
public class User {

    @Id
    @Column(name = "name")
    public String name;

    @Column(name = "password")
    public String password;

    @Column(name = "enabled")
    public String enabled;

    @Column(name = "authority")
    public String authority;
}
