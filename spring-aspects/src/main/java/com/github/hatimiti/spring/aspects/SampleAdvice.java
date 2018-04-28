package com.github.hatimiti.spring.aspects;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Aspect
@Component
public class SampleAdvice {

    @Before("execution(* *..*Controller.*(..))")
    public void before() {
        System.out.println("prints Before by AOP");
    }
}
