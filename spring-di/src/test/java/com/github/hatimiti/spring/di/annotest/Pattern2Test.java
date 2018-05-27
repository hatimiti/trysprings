package com.github.hatimiti.spring.di.annotest;

import com.github.hatimiti.spring.di.Main;
import com.github.hatimiti.spring.di.annotest.Pattern2.P2Autowired;
import com.github.hatimiti.spring.di.annotest.Pattern2.P2Inject;
import com.github.hatimiti.spring.di.annotest.Pattern2.P2Resource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@SpringJUnitConfig
public class Pattern2Test {

    @Autowired
    P2Autowired autowired;

    @Autowired
    P2Inject inject;

    @Autowired
    P2Resource resource;

    /**
     * <pre>〜 @Autowired／@Inject／@Resource の違い - フィールド名での判定 - 〜
     * ■ いずれもインジェクションは成功し、フィールド名 == Bean ID として判定される。
     * </pre> */
    @Test
    void test() {
        Assertions.assertEquals("Circle", autowired.circle.form());
        Assertions.assertEquals("Circle", inject.circle.form());
        Assertions.assertEquals("Circle", resource.circle.form());
    }

    @Import(Main.class)
    @Configuration
    static class TestConfig {
    }
}
