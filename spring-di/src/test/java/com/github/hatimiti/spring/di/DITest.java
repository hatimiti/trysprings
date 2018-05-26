package com.github.hatimiti.spring.di;

import com.github.hatimiti.spring.nondi.NonDIService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ContextConfiguration
@ExtendWith(SpringExtension.class)
// -> @SpringJUnitConfig
public class DITest {

    @Autowired
    ApplicationContext context;

    @BeforeAll
    static void beforeAll() {
    }

    /**
     * <pre>〜 @Scope の動きを確認する 〜
     * ■ 各 Bean は指定された Scope の条件に沿ってコンテナ(ApplicationContext)内に生成／破棄される。
     * ■ 主なスコープ
     *   ・singleton (default) …… 1オブジェクトのみ生成され、コンテナ終了時に破棄される。
     *   ・prototype …… getBean() するたびに生成される。生成直後にコンテナ管理外となる。
     *   ・session …… Web でのみ利用可能。HttpSession の範囲で生成／破棄される。
     *   ・request …… Web でのみ利用可能。HttpRequest の範囲で生成／破棄される。
     * ■ スコープの指定は該当 Bean に @Scope("prototype") のように指定する。
     * ■ No.1 は singleton のため、s1 == s2 となる。
     * ■ No.2 は prototype のため、s3 != s4 となる。
     * </pre> */
    @Test
    public void testScope() {
        /* No.1 */
        DIService s1 = context.getBean("diAnnotationService", DIServiceImpl.class);
        DIService s2 = context.getBean("diAnnotationService", DIServiceImpl.class);
        assertTrue(s1 == s2);

        /* No.2 */
        DIService s3 = context.getBean("diJavaConfigService", DIServiceImpl.class);
        DIService s4 = context.getBean("diJavaConfigService", DIServiceImpl.class);
        assertTrue(s3 != s4);
    }

    /**
     * <pre>〜 @ComponentScan の動きを確認する 〜
     * ■ @ComponentScan は @Component が付加されたクラスを検索する。
     * ■ @Component と同等の動きをするステレオタイプアノテーションが存在し、これらもスキャン対象となる。
     *   ・@Controller
     *   ・@RestController
     *   ・@Service
     *   ・@Repository
     *   ・@Configuration
     * ■ @ComponentScan が付加されているのは com.github.hatimiti.spring.di.Main クラスのため
     *   di パッケージ配下のみがコンポーネントの検索対象となる。
     * ■ com.github.hatimiti.spring.nondi パッケージは @ComponentScan 対象外のため
     * ■ No.1 はスキャン対象外のため Bean 取得時に NoSuchBeanDefinitionException が発生する。
     * ■ No.2 は DIConfig クラスに明示的に @Bean 定義しているため Bean 取得可能。
     * </pre> */
    @Test
    public void testComponentScan() {
        /* ■ No.1 */
        assertThrows(NoSuchBeanDefinitionException.class,
                () -> context.getBean("nonDIAnnotationService", NonDIService.class));
        /* ■ No.2 */
        assertNotNull(context.getBean("nonDIJavaConfigService", NonDIService.class));
    }

    @Test
    public void testAOP() {
        DIService s1 = context.getBean("diAnnotationService", DIServiceImpl.class);
        assertEquals("hello DI Service", s1.hello());
    }

    @Import(Main.class)
    @Configuration
    static class TestConfig {
    }
}
