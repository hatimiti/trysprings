package com.github.hatimiti.spring.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.client.OAuth2ClientAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.oauth2.client.CommonOAuth2Provider;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.InMemoryOAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.endpoint.NimbusAuthorizationCodeTokenResponseClient;
import org.springframework.security.oauth2.client.endpoint.OAuth2AccessTokenResponseClient;
import org.springframework.security.oauth2.client.endpoint.OAuth2AuthorizationCodeGrantRequest;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.AuthorizationRequestRepository;
import org.springframework.security.oauth2.client.web.HttpSessionOAuth2AuthorizationRequestRepository;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;

import javax.sql.DataSource;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@EnableWebSecurity
@Import(OAuth2ClientAutoConfiguration.class)
@PropertySource("classpath:/application.yml")
@Configuration
public class SampleSecurityOAuth2Config extends WebSecurityConfigurerAdapter {

    private static List<String> OAUTH2_CLIENTS = Arrays.asList("google", "facebook", "github", "twitter");
    private static String CLIENT_PROPERTY_KEY = "spring.security.oauth2.client.registration.";

    @Autowired private DataSource dataSource;
    @Autowired private Environment env;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .mvcMatchers("/oauth_login") // NOTE: 自身で定義したログインページのみアクセス許可する
                .permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .oauth2Login()
                .loginPage("/oauth_login") // NOTE: 自身でログインページのレイアウトを定義したい場合に指定する。
                .clientRegistrationRepository(clientRegistrationRepository())
                .authorizedClientService(authorizedClientService())
                .authorizationEndpoint()
                    .authorizationRequestRepository(authorizationRequestRepository())
                .and()
                .tokenEndpoint()
                    .accessTokenResponseClient(accessTokenResponseClient())
        ;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication()
                .dataSource(dataSource)
                .usersByUsernameQuery("SELECT name as username, password, enabled FROM M_USER WHERE name = ?")
                .authoritiesByUsernameQuery("SELECT name as username, authority FROM M_USER WHERE name = ?")
                .passwordEncoder(new BCryptPasswordEncoder());
    }

    @Bean
    public OAuth2AuthorizedClientService authorizedClientService() {
        return new InMemoryOAuth2AuthorizedClientService(clientRegistrationRepository());
    }

    @Bean
    public ClientRegistrationRepository clientRegistrationRepository() {
        List<ClientRegistration> registrations = OAUTH2_CLIENTS.stream()
                .map(c -> getRegistration(c))
                .filter(registration -> registration != null)
                .collect(Collectors.toList());

        return new InMemoryClientRegistrationRepository(registrations);
    }

    /*
     * NOTE: Authorization Endpoint を独自カスタマイズしたい場合に定義する
     */
    @Bean
    public AuthorizationRequestRepository<OAuth2AuthorizationRequest> authorizationRequestRepository() {
        return new HttpSessionOAuth2AuthorizationRequestRepository();
    }

    /*
     *  NOTE: Token Endpoint を独自にカスタマイズしたい場合に定義する
     */
    @Bean
    public OAuth2AccessTokenResponseClient<OAuth2AuthorizationCodeGrantRequest> accessTokenResponseClient() {
        return new NimbusAuthorizationCodeTokenResponseClient();
    }

    private ClientRegistration getRegistration(String client) {
        String clientId = env.getProperty(CLIENT_PROPERTY_KEY + client + ".client-id");

        if (clientId == null) {
            return null;
        }

        String clientSecret = env.getProperty(
                CLIENT_PROPERTY_KEY + client + ".client-secret");

        if (client.equals("google")) {
            return CommonOAuth2Provider.GOOGLE.getBuilder(client)
                    .clientId(clientId).clientSecret(clientSecret).build();
        }
        if (client.equals("facebook")) {
            return CommonOAuth2Provider.FACEBOOK.getBuilder(client)
                    .clientId(clientId).clientSecret(clientSecret).build();
        }
        if (client.equals("github")) {
            return CommonOAuth2Provider.GITHUB.getBuilder(client)
                    .clientId(clientId).clientSecret(clientSecret).build();
        }
        if (client.equals("twitter")) {
            return createTwitterRegistration(client)
                    .clientId(clientId).clientSecret(clientSecret).build();
        }
        return null;
    }

    /*
     * NOTE: AuthorizationGrantType に client_credentials が定義されていないため現時点で実装不可
     */
    private ClientRegistration.Builder createTwitterRegistration(String client) {

        ClientRegistration.Builder builder = ClientRegistration.withRegistrationId(client);
        builder.clientAuthenticationMethod(ClientAuthenticationMethod.POST);
        builder.authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE); // client_credentials が選択できない
        builder.redirectUriTemplate(DEFAULT_LOGIN_REDIRECT_URL);

        return builder.scope("Read only")
                .tokenUri("https://api.twitter.com/oauth2/token")
                .authorizationUri("https://api.twitter.com/oauth/authorize")
                .userInfoUri("https://api.twitter.com/1.1/users/show.json")
                .userNameAttributeName("id")
                .clientName("Twitter");
    }

    private static final String DEFAULT_LOGIN_REDIRECT_URL = "{baseUrl}/login/oauth2/code/{registrationId}";

}
