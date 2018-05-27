package com.github.hatimiti.spring.di.annotest;

import org.springframework.stereotype.Component;

@Component
public class Square implements Shape {
    @Override
    public String form() {
        return "Square";
    }
}
