package com.github.hatimiti.spring.di;

import org.springframework.stereotype.Service;

@Service("diService")
public class DIServiceImpl implements DIService {
    @Override
    public String hello() {
        return "hello DI Service";
    }
}
