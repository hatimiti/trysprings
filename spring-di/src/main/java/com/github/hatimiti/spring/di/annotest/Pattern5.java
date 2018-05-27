package com.github.hatimiti.spring.di.annotest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.inject.Inject;

public class Pattern5 {

    @Component @Lazy
    public static class P5Autowired {
        @Autowired
        Animal animal;
    }

    @Component @Lazy
    public static class P5Inject {
        @Inject
        Animal animal;
    }

    @Component @Lazy
    public static class P5Resource {
        @Resource
        Animal animal;
    }

    @Component @Lazy
    public static class P5ResourceName {
        @Resource(name = "dog")
        Animal animal;
    }

    public static interface Animal {
        String hello();
    }

    @Component("dog")
    public static class Dog implements Animal {
        @Override
        public String hello() {
            return "Wan!";
        }
    }
}
