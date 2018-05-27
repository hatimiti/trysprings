package com.github.hatimiti.spring.di.annotest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.inject.Inject;

public class Pattern2 {

    @Component @Lazy
    public static class P2Autowired {
        @Autowired
        Shape circle;
    }

    @Component @Lazy
    public static class P2Inject {
        @Inject
        Shape circle;
    }

    @Component @Lazy
    public static class P2Resource {
        @Resource
        Shape circle;
    }
}
