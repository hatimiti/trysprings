package com.github.hatimiti.spring.di;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <pre>〜 @Service の理解 〜
 * ■ 基本的に @Component と違いは無い
 * ■ Service 層のオブジェクトであることを明示する。
 * </pre> */
@Service("diAnnotationService")
public class DIServiceImpl implements DIService {

    /**
     * <pre>〜 フィールドインジェクション 〜
     * ■ コンストラクタ、Setter を用意する必要が無い
     * ■ コード量は一番少ない
     * ■ フィールドを final にできる。
     * ■ テスト時にモックオブジェクトに置き換えづらい
     * </pre> */
//    @Autowired
    private /*final*/ DIRepository diRepository;

    /**
     * <pre>〜 コンストラクタインジェクション 〜
     * ■ Setter を用意する必要が無い
     * ■ フィールドを final にできる。
     * ■ テスト時にモックオブジェクトに置き換えやすい
     * </pre> */
    @Autowired
    public DIServiceImpl(final DIRepository diRepository) {
        this.diRepository = diRepository;
    }

    @Override
    public String hello() {
        return this.diRepository.findAllUsers().toString();
    }

    /**
     * <pre>〜 Setter インジェクション 〜
     * ■ コンストラクタを用意する必要が無い
     * ■ フィールドを final にできない。
     * ■ コード量が多くなる。
     * ■ テスト時にモックオブジェクトに置き換えやすい
     * </pre> */
//    @Autowired
//    public void setDiRepository(DIRepository diRepository) {
//        this.diRepository = diRepository;
//    }
    /*
    "main@1" prio=5 tid=0x1 nid=NA runnable
      java.lang.Thread.State: RUNNABLE
        at com.github.hatimiti.spring.di.DIServiceImpl.setDiRepository(DIServiceImpl.java:51)
        at sun.reflect.NativeMethodAccessorImpl.invoke0(NativeMethodAccessorImpl.java:-1)
        at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)
        at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
        at java.lang.reflect.Method.invoke(Method.java:498)
        at org.springframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor$AutowiredMethodElement.inject(AutowiredAnnotationBeanPostProcessor.java:699)
        at org.springframework.beans.factory.annotation.InjectionMetadata.inject(InjectionMetadata.java:91)
        at org.springframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor.postProcessPropertyValues(AutowiredAnnotationBeanPostProcessor.java:373)
        at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.populateBean(AbstractAutowireCapableBeanFactory.java:1344)
        at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.doCreateBean(AbstractAutowireCapableBeanFactory.java:578)
        at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.createBean(AbstractAutowireCapableBeanFactory.java:501)
        at org.springframework.beans.factory.support.AbstractBeanFactory.lambda$doGetBean$0(AbstractBeanFactory.java:317)
        at org.springframework.beans.factory.support.AbstractBeanFactory$$Lambda$218.1904288897.getObject(Unknown Source:-1)
        at org.springframework.beans.factory.support.DefaultSingletonBeanRegistry.getSingleton(DefaultSingletonBeanRegistry.java:228)
        - locked <0xe35> (a java.util.concurrent.ConcurrentHashMap)
        at org.springframework.beans.factory.support.AbstractBeanFactory.doGetBean(AbstractBeanFactory.java:315)
        at org.springframework.beans.factory.support.AbstractBeanFactory.getBean(AbstractBeanFactory.java:199)
        at org.springframework.beans.factory.support.DefaultListableBeanFactory.preInstantiateSingletons(DefaultListableBeanFactory.java:760)
        at org.springframework.context.support.AbstractApplicationContext.finishBeanFactoryInitialization(AbstractApplicationContext.java:869)
        at org.springframework.context.support.AbstractApplicationContext.refresh(AbstractApplicationContext.java:550)
     */
}
