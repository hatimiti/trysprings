package com.github.hatimiti.spring.dummy;

import com.github.hatimiti.spring.dummy.Main;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@SpringJUnitConfig
public class DummyTest {

    @BeforeAll
    static void beforeAll() {
    }

    @Test
    public void testDummy() {
    }

    @Import(Main.class)
    @Configuration
    static class TestConfig {
    }
}
