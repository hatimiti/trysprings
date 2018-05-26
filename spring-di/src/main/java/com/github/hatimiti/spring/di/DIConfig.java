package com.github.hatimiti.spring.di;

import com.github.hatimiti.spring.nondi.NonDIRepositoryImpl;
import com.github.hatimiti.spring.nondi.NonDIService;
import com.github.hatimiti.spring.nondi.NonDIServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

import static org.springframework.beans.factory.config.ConfigurableBeanFactory.SCOPE_PROTOTYPE;

/**
 * <pre>〜 @Configuration の理解 〜
 * ■ Bean 定義 (@Beanの利用) のためにクラスに対して付加する。
 * ■ @Configuration も @Component のステレオタイプアノテーション
 * ■ final class にしてはいけない
 * ■ 内部クラスとして定義する場合は static class にする必要がある。
 * </pre> */
@Configuration
public class DIConfig {

    /**
     * <pre>〜 @Configuration の理解 〜
     * ■ 主なスコープの指定
     *   ・org.springframework.beans.factory.config.ConfigurableBeanFactory
     *     #SCOPE_SINGLETON == "singleton" (default)
     *     #SCOPE_PROTOTYPE == "prototype"
     *   ・org.springframework.web.context.WebApplicationContext
     *     #SCOPE_SESSION == "session"
     *     #SCOPE_REQUEST == "request"
     * </pre> */
    @Bean
    @Scope(SCOPE_PROTOTYPE) // == @Scope("prototype")
    public DIService diJavaConfigService(final DIRepository diRepository) {
        /* @Bean メソッドの引数に Spring が自動的にコンテナ内の
         * オブジェクトを渡してくれるため依存度低い */
        return new DIServiceImpl(diRepository);
    }

    /**
     * <pre>〜 @Bean の理解 〜
     * ■ @Configuration が付加されたクラス内のメソッドに対して @Bean を付加すると
     *  その返り値がコンテナ(ApplicationContext)管理オブジェクトとして登録される。
     * ■ Bean ID(名前) は、デフォルトはメソッド名が利用される。(この Bean の場合は "nonDIJavaConfigService" )
     * ■ Bean ID を変更したい場合は @Bean("otherName") のように指定する。
     * ■ Bean ID は Bean を取得する際 (get.Bean("<Bean ID>")) に利用する。
     * </pre> */
    @Bean//("otherName")
    public NonDIService nonDIJavaConfigService() {
        // 直接 NonDIRepository を new しているため依存度高い
        return new NonDIServiceImpl(new NonDIRepositoryImpl());
    }

    @Bean
    public PlatformTransactionManager transactionManager() {
        return new DataSourceTransactionManager(dataSource());
    }

    @Bean
    public DataSource dataSource() {
        return new EmbeddedDatabaseBuilder().build();
    }
}
