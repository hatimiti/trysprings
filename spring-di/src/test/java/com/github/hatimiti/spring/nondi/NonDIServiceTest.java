package com.github.hatimiti.spring.nondi;

import com.github.hatimiti.spring.di.Main;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.Mockito.when;

@SpringJUnitConfig
public class NonDIServiceTest {

    @Autowired
    @Qualifier("nonDIJavaConfigService")
    NonDIService nonDIService;

    @MockBean
    NonDIRepository mockDIRepository;

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
        /* when */
        when(mockDIRepository.findAllUsers()).thenReturn(Collections.emptyList());

        /* then */
        // ↑で mockito でリストを空にしているが Service が返す値が変わっていない
        assertNotEquals("[]", nonDIService.hello(), nonDIService.hello());
    }

    @Import(Main.class)
    @Configuration
    static class TestConfig {
    }
}
