package com.github.hatimiti.spring.di.annotest;

import com.github.hatimiti.spring.di.Main;
import com.github.hatimiti.spring.di.annotest.Pattern1.P1Autowired;
import com.github.hatimiti.spring.di.annotest.Pattern1.P1Inject;
import com.github.hatimiti.spring.di.annotest.Pattern1.P1Resource;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.NoUniqueBeanDefinitionException;
import org.springframework.beans.factory.UnsatisfiedDependencyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Lazy;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringJUnitConfig
public class Pattern1Test {

    @Autowired @Lazy
    P1Autowired autowired;

    @Autowired @Lazy
    P1Inject inject;

    @Autowired @Lazy
    P1Resource resource;

    /**
     * <pre>〜 @Autowired／@Inject／@Resource の違い - 型での判定 - 〜
     * ■ 対象 Bean がユニークで無い場合はいずれも例外が発生する。
     * ■ 直接的に発生する例外は @Autowired／@Inject は UnsatisfiedDependencyException、@Resource は BeanCreationException
     * ■ 根本的に発生する例外はいずれも NoUniqueBeanDefinitionException
     * </pre> */
    @Test
    void test() {
        assertCauseWithNoUniqueBeanDefinitionException(assertThrows(UnsatisfiedDependencyException.class, () -> System.out.println(autowired)));
        assertCauseWithNoUniqueBeanDefinitionException(assertThrows(UnsatisfiedDependencyException.class, () -> System.out.println(inject)));
        assertCauseWithNoUniqueBeanDefinitionException(assertThrows(BeanCreationException.class, () -> System.out.println(resource)));
    }

    private void assertCauseWithNoUniqueBeanDefinitionException(Throwable t) {
        assertEquals(NoUniqueBeanDefinitionException.class, t.getCause().getClass());
    }

    @Import(Main.class)
    @Configuration
    static class TestConfig {
    }

}
