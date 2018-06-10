package com.github.hatimiti.spring.lifecycle;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Scope("prototype")
@Service
public class LifeCyclePrototypeService implements InitializingBean, DisposableBean {

    static {
        System.out.println("P - STATIC: " + getSimpleClassName());
    }

    public LifeCyclePrototypeService() {
        System.out.println("P - CONSTRUCTOR(): " + getSimpleClassName());
    }

    public void hello() {
        System.out.println("P - hello(): " + getSimpleClassName());
    }

    @PostConstruct
    public void postConstruct() {
        System.out.println("P - postConstruct(): " + getSimpleClassName());
    }

    @PreDestroy
    public void preDestroy() {
        System.out.println("P - preDestroy(): " + getSimpleClassName());
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("P - afterPropertiesSet(): " + getSimpleClassName());
    }

    @Override
    public void destroy() throws Exception {
        System.out.println("P - destroy(): " + getSimpleClassName());
    }

    private static String getSimpleClassName() {
        return LifeCyclePrototypeService.class.getSimpleName();
    }

}
