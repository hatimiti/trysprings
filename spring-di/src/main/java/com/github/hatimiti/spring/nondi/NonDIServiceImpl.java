package com.github.hatimiti.spring.nondi;

import org.springframework.stereotype.Service;

@Service
public class NonDIServiceImpl implements NonDIService {
    @Override
    public String hello() {
        return "hello non DI Service";
    }
}
