package com.github.hatimiti.spring.di.annotest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.inject.Inject;

public class Pattern1 {

    @Component @Lazy
    public static class P1Autowired {
        @Autowired
        Shape shape;
    }

    @Component @Lazy
    public static class P1Inject {
        @Inject
        Shape shape;
    }

    @Component @Lazy
    public static class P1Resource {
        @Resource
        Shape shape;
    }
}
