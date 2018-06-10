package com.github.hatimiti.spring.lifecycle;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.Arrays;

@Service
public class LifeCycleService implements InitializingBean, DisposableBean {

    private static boolean isPrintableStackTrace = false;

    static {
        System.out.println("S - STATIC: " + getSimpleClassName());
        printStackTrace();
    }

    public LifeCycleService() {
        System.out.println("S - CONSTRUCTOR(): " + getSimpleClassName());
        printStackTrace();
    }

    public void hello() {
        System.out.println("S - hello(): " + getSimpleClassName());
        printStackTrace();
    }

    @PostConstruct
    public void postConstruct() {
        System.out.println("S - postConstruct(): " + getSimpleClassName());
        printStackTrace();
    }

    @PreDestroy
    public void preDestroy() {
        System.out.println("S - preDestroy(): " + getSimpleClassName());
        printStackTrace();
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("S - afterPropertiesSet(): " + getSimpleClassName());
        printStackTrace();
    }

    @Override
    public void destroy() throws Exception {
        System.out.println("S - destroy(): " + getSimpleClassName());
        printStackTrace();
    }

    private static String getSimpleClassName() {
        return LifeCycleService.class.getSimpleName();
    }

    private static void printStackTrace() {
        if (!isPrintableStackTrace) {
            return;
        }
        Arrays.asList(new RuntimeException().getStackTrace()).forEach(System.out::println);
        System.out.println();
    }

}
