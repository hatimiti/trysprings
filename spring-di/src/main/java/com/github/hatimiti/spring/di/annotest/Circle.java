package com.github.hatimiti.spring.di.annotest;

import org.springframework.stereotype.Component;

@Component
public class Circle implements Shape {
    @Override
    public String form() {
        return "Circle";
    }
}
