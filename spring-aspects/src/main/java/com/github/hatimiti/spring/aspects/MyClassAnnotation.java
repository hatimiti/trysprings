package com.github.hatimiti.spring.aspects;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MyClassAnnotation {

    String value() default "";

}