package com.github.hatimiti.spring.lifecycle;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanClassLoaderAware;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.context.*;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

@Component
public class LifeCycleAware implements
        BeanNameAware, // by Bean
        BeanClassLoaderAware, // by Bean
        BeanFactoryAware, // by Bean
        ResourceLoaderAware,
        ApplicationEventPublisherAware,
        MessageSourceAware,
        ApplicationContextAware {

    @Override
    public void setBeanName(
            final String name) {

        System.out.println("1: BeanNameAware");
        System.out.println("   BeanName = " + name);
    }

    @Override
    public void setBeanClassLoader(
            final ClassLoader classLoader) {

        System.out.println("2: BeanClassLoaderAware");
        System.out.println("   BeanClassLoader = " + classLoader);
    }

    @Override
    public void setBeanFactory(
            final BeanFactory beanFactory) throws BeansException {

        System.out.println("3: BeanFactoryAware");
        System.out.println("   BeanFactory = " + beanFactory);
    }

    @Override
    public void setResourceLoader(
            final ResourceLoader resourceLoader) {

        System.out.println("4: ResourceLoaderAware");
        System.out.println("   ResourceLoader = " + resourceLoader);
    }

    @Override
    public void setApplicationEventPublisher(
            final ApplicationEventPublisher applicationEventPublisher) {

        System.out.println("5: ApplicationEventPublisherAware");
        System.out.println("   ApplicationEventPublisher = " + applicationEventPublisher);
    }

    @Override
    public void setMessageSource(
            final MessageSource messageSource) {

        System.out.println("6: MessageSourceAware");
        System.out.println("   MessageSource = " + messageSource);
    }

    @Override
    public void setApplicationContext(
            final ApplicationContext applicationContext) throws BeansException {

        System.out.println("7: ApplicationContextAware");
        System.out.println("   ApplicationContext = " + applicationContext);
    }

}
