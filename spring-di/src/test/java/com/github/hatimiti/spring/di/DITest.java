package com.github.hatimiti.spring.di;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@ContextConfiguration
@ExtendWith(SpringExtension.class)
// -> @SpringJUnitConfig
public class DITest {

    @Autowired
    ApplicationContext context;

    @BeforeAll
    static void beforeAll() {
    }

    @Test
    public void testScope() {
        DIServiceImpl s1 = context.getBean("diService", DIServiceImpl.class);
        DIServiceImpl s2 = context.getBean("diService", DIServiceImpl.class);
        assertEquals(s1, s2);

        DIServiceImpl s3 = context.getBean("diPrototypeService", DIServiceImpl.class);
        DIServiceImpl s4 = context.getBean("diPrototypeService", DIServiceImpl.class);
        assertNotEquals(s3, s4);
    }

    @Import(Main.class)
    @Configuration
    static class TestConfig {
    }
}
