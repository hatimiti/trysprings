package com.github.hatimiti.spring.di.proxymode;

import com.github.hatimiti.spring.di.Main;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import static org.junit.jupiter.api.Assertions.*;

@SpringJUnitConfig
public class ProxyModeTest {

    @Autowired
    ApplicationContext context;

    /**
     * <pre>〜 〜
     * </pre> */
    @Test
    void testHello1() {
        /* when */
        ModeServiceImpl impl1 = (ModeServiceImpl) context.getBean(ModeService.class);
        ModeServiceImpl impl2 = (ModeServiceImpl) context.getBean(ModeService.class);

        /* then */

        /**
         * Proxy を利用しない場合、prototype で定義していても、singleton 内に DI されたオブジェクトは
         * singleton に丸められてしまう。
         */
        assertTrue("NoProxyServiceImpl".equals(impl1.noProxyService.getClass().getSimpleName()), impl1.noProxyService.getClass().getName());
        assertEquals(1, impl1.countByNoProxy());
        assertEquals(2, impl2.countByNoProxy());
        assertEquals(3, impl1.countByNoProxy());
        assertEquals(4, impl2.countByNoProxy());

        /**
         * Proxy を利用した場合、singleton 内に DI した場合でも、該当クラス継承した CGLIB のクラスが間に入り
         * Bean を都度生成してくれる。
         * 但し、利用の都度毎回生成されるため状態は保持できない。
         * また、継承するため対象 Bean クラスを final にしたり、メソッドを final にしてはいけない。
         */
        assertTrue(impl1.proxyTargetClassService.getClass().getSimpleName().contains("ProxyTargetClassServiceImpl$$EnhancerBySpringCGLIB"));
        assertTrue(impl1.proxyTargetClassService instanceof ProxyTargetClassServiceImpl);
        assertEquals(1, impl1.countByProxy());
        assertEquals(1, impl2.countByProxy());
        assertEquals(1, impl1.countByProxy());
        assertEquals(1, impl2.countByProxy());
    }


    @Import(Main.class)
    @Configuration
    static class TestConfig {
    }
}
