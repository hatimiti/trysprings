package com.github.hatimiti.spring.common;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@SpringJUnitConfig
public class UtilsTest {

    @BeforeAll
    static void beforeAll() {
    }

    @Test
    public void testDummy() {
    }

    @Import(Utils.class)
    @Configuration
    static class TestConfig {
    }
}
