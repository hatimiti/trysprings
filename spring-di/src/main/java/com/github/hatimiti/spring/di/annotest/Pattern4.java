package com.github.hatimiti.spring.di.annotest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.inject.Inject;

public class Pattern4 {

    @Component @Lazy
    public static class P4Autowired {
        @Autowired
        @Qualifier("circle")
        Shape square;
    }

    @Component @Lazy
    public static class P4Inject {
        @Inject
        @Qualifier("circle")
        Shape square;
    }

    @Component @Lazy
    public static class P4Resource {
        @Resource
        @Qualifier("circle")
        Shape square;
    }

    @Component @Lazy
    public static class P4ResourceName {
        @Resource(name = "circle")
        Shape square;
    }
}
