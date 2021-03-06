package com.github.hatimiti.spring.aspects;

import org.springframework.stereotype.Service;

@MyClassAnnotation
@Service
public class SampleServiceImpl implements SampleService {

    public String x() { return "x()"; }

    public String y() { return "y()"; }

    @MyAnnotation
    public String z() { return "z()"; }

    @MyAnnotation("sample")
    public String z2() { return "z2()"; }

    @MyAnnotation2
    public String z3() { return "z3()"; }

}
