package com.github.hatimiti.spring.di.annotest;

import com.github.hatimiti.spring.di.Main;
import com.github.hatimiti.spring.di.annotest.Pattern4.P4Autowired;
import com.github.hatimiti.spring.di.annotest.Pattern4.P4Inject;
import com.github.hatimiti.spring.di.annotest.Pattern4.P4Resource;
import com.github.hatimiti.spring.di.annotest.Pattern4.P4ResourceName;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@SpringJUnitConfig
public class Pattern4Test {

    @Autowired
    P4Autowired autowired;

    @Autowired
    P4Inject inject;

    @Autowired
    P4Resource resource;

    @Autowired
    P4ResourceName resourceName;

    /**
     * <pre>〜 @Autowired／@Inject／@Resource の違い - @Qualifier&フィールド名 での判定 - 〜
     * ■ いずれもインジェクションは成功する。
     * ■ @Resource は @Qualifiler.value よりもフィールド名が優先される。
     * ■ 番外: @Resource(name = "circle") と指定するとフィールド名より優先される。
     * </pre> */
    @Test
    void test() {
        Assertions.assertEquals("Circle", autowired.square.form());
        Assertions.assertEquals("Circle", inject.square.form());
        Assertions.assertEquals("Square", resource.square.form());
        Assertions.assertEquals("Circle", resourceName.square.form());
    }

    @Import(Main.class)
    @Configuration
    static class TestConfig {
    }
}
