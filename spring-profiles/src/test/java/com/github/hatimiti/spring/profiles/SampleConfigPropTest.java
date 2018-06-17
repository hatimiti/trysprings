package com.github.hatimiti.spring.profiles;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.context.properties.bind.validation.BindValidationException;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class SampleConfigPropTest {

    @SpringJUnitConfig
    static class BaseTest {
        @BeforeAll
        static void beforeAll() {
            System.setProperty("ENV", "dev");
        }
    }

    @ActiveProfiles("valid1")
    static class Valid1Test extends BaseTest {

        @Test
        public void testValid1() {
            try {
                System.out.println("----------- start -------------");
                ConfigurableApplicationContext run = SpringApplication.run(Main.class);
            } finally {
                System.out.println("----------- end -------------");
            }
//            assertThrows(BindValidationException.class, () -> SpringApplication.run(Main.class));
//            assertThrows(IllegalStateException.class, () -> SpringApplication.run(Main.class));
        }
    }

    @Import(Main.class)
    @Configuration
    static class TestConfig {
    }

}
