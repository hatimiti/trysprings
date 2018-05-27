package com.github.hatimiti.spring.di.annotest;

import com.github.hatimiti.spring.di.Main;
import com.github.hatimiti.spring.di.annotest.Pattern5.P5Autowired;
import com.github.hatimiti.spring.di.annotest.Pattern5.P5Inject;
import com.github.hatimiti.spring.di.annotest.Pattern5.P5Resource;
import com.github.hatimiti.spring.di.annotest.Pattern5.P5ResourceName;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@SpringJUnitConfig
public class Pattern5Test {

    @Autowired
    P5Autowired autowired;

    @Autowired
    P5Inject inject;

    @Autowired
    P5Resource resource;

    @Autowired
    P5ResourceName resourceName;

    /**
     * <pre>〜 @Autowired／@Inject／@Resource の違い - ユニークなBeanの場合の判定 - 〜
     * ■ いずれもインジェクションは成功する。
     * ■ フィールド名と型名が異なっていても(Animal - Dog)例外にはならない。
     * </pre> */
    @Test
    void test() {
        Assertions.assertEquals("Wan!", autowired.animal.hello());
        Assertions.assertEquals("Wan!", inject.animal.hello());
        Assertions.assertEquals("Wan!", resource.animal.hello());
        Assertions.assertEquals("Wan!", resourceName.animal.hello());
    }

    @Import(Main.class)
    @Configuration
    static class TestConfig {
    }
}
