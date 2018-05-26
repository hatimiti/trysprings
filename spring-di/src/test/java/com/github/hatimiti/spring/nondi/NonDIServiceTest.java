package com.github.hatimiti.spring.nondi;

import com.github.hatimiti.spring.di.Main;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringJUnitConfig
public class NonDIServiceTest {

    @Autowired
    @Qualifier("nonDIJavaConfigService")
    NonDIService nonDIService;

    @BeforeAll
    static void beforeAll() {
    }

    /**
     * <pre>〜 〜
     * ■ nonDIService がフィールドに保持している nonDIRepository の結合度が高いため
     *   テスト中に mock で置き換えることができない。(nonDIService毎作り直す必要がある)
     * </pre> */
    @Test
    void testHello() {
        assertNotEquals("[]", nonDIService.hello(), nonDIService.hello());
    }

    @Import(Main.class)
    @Configuration
    static class TestConfig {
    }
}
