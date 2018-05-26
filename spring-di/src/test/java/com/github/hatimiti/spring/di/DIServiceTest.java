package com.github.hatimiti.spring.di;

import com.github.hatimiti.spring.common.db.entity.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@SpringJUnitConfig
public class DIServiceTest {

    @Autowired
    @Qualifier("diAnnotationService")
    DIService diService;

    /**
     * <pre>〜 @MockBean でテスト用オブジェクトを DI する 〜
     * ■ diService がフィールドに保持している diRepository の結合度が低いため
     *   テスト中に mock で置き換えることができる。
     * </pre> */
    @MockBean
    DIRepository mockDIRepository;

    @BeforeAll
    static void beforeAll() {
    }

    @AfterEach
    void afterEach() {
        // モックの状態をリセットしなくてもテスト毎に自動でリセットされている
        // Mockito.reset(mockDIRepository);
    }

    /**
     * <pre>〜 〜
     * </pre> */
    @Test
    void testHello1() {
        /* when */
        when(mockDIRepository.findAllUsers()).thenReturn(Collections.emptyList());

        /* then */
        assertEquals("[]", diService.hello());
    }

    @Test
    void testHello2() {
        /* when */
        final User u = new User();
        u.userId = 3L;
        u.password = "password";
        when(mockDIRepository.findAllUsers()).thenReturn(Arrays.asList(u));

        /* then */
        assertTrue(diService.hello().matches(
                "\\[\\[User@[a-zA-Z0-9]+ userId = 3, name = \\[null\\], password = 'password'\\]\\]"));
    }

    @Import(Main.class)
    @Configuration
    static class TestConfig {
    }
}
