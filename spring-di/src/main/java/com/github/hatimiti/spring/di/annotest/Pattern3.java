package com.github.hatimiti.spring.di.annotest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.inject.Inject;

public class Pattern3 {

    @Component @Lazy
    public static class P3Autowired {
        @Autowired
        @Qualifier("circle")
        Shape shape;
    }

    @Component @Lazy
    public static class P3Inject {
        @Inject
        @Qualifier("circle")
        Shape shape;
    }

    @Component @Lazy
    public static class P3Resource {
        @Resource
        @Qualifier("circle")
        Shape shape;
    }
}
