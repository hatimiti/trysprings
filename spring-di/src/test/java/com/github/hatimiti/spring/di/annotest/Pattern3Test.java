package com.github.hatimiti.spring.di.annotest;

import com.github.hatimiti.spring.di.Main;
import com.github.hatimiti.spring.di.annotest.Pattern3.P3Autowired;
import com.github.hatimiti.spring.di.annotest.Pattern3.P3Inject;
import com.github.hatimiti.spring.di.annotest.Pattern3.P3Resource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@SpringJUnitConfig
public class Pattern3Test {

    @Autowired
    P3Autowired autowired;

    @Autowired
    P3Inject inject;

    @Autowired
    P3Resource resource;

    /**
     * <pre>〜 @Autowired／@Inject／@Resource の違い - @Qualifier での判定 - 〜
     * ■ いずれもインジェクションは成功し、@Qualifier.value == Bean ID として判定される。
     * </pre> */
    @Test
    void test() {
        Assertions.assertEquals("Circle", autowired.shape.form());
        Assertions.assertEquals("Circle", inject.shape.form());
        Assertions.assertEquals("Circle", resource.shape.form());
    }

    @Import(Main.class)
    @Configuration
    static class TestConfig {
    }
}
