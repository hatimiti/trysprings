package com.github.hatimiti.spring.aspects;

import org.springframework.stereotype.Service;

@Service
public class SampleService {

    public String xxxYyyZzz() {
        return "xxxYyyZzz";
    }

    public String yyyZzzXxx() {
        return "yyyZzzXxx";
    }

    @MyAnnotation
    public String zzzXxxYyy() {
        return "zzzXxxYyy";
    }

    @MyAnnotation("sample")
    public String zzzXxxYyy2() {
        return "zzzXxxYyy2";
    }

    @MyAnnotation2
    public String myAnnotation2() {
        return "myAnnotation2";
    }

}
