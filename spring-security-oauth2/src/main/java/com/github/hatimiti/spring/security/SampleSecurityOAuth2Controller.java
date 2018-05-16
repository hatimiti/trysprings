package com.github.hatimiti.spring.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ResolvableType;
import org.springframework.http.HttpHeaders;
import org.springframework.http.RequestEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Controller
public class SampleSecurityOAuth2Controller {

    private static String authorizationRequestBaseUri = "oauth2/authorization";
    private final Map<String, String> oauth2AuthenticationUrls = new ConcurrentHashMap<>();

    private final OAuth2AuthorizedClientService authorizedClientService;
    private final ClientRegistrationRepository clientRegistrationRepository;

    private final RestOperations restTemplate = new RestTemplate();

    @Autowired
    public SampleSecurityOAuth2Controller(
            final OAuth2AuthorizedClientService authorizedClientService,
            final ClientRegistrationRepository clientRegistrationRepository) {
        this.authorizedClientService = authorizedClientService;
        this.clientRegistrationRepository = clientRegistrationRepository;
    }

    @GetMapping("/oauth_login")
    public String getLoginPage(final Model model) {
        Iterable<ClientRegistration> clientRegistrations = null;
        final ResolvableType type = ResolvableType.forInstance(clientRegistrationRepository).as(Iterable.class);
        if (type != ResolvableType.NONE
                && ClientRegistration.class.isAssignableFrom(type.resolveGenerics()[0])) {
            clientRegistrations = (Iterable<ClientRegistration>) clientRegistrationRepository;
        }

        clientRegistrations.forEach(registration ->
                oauth2AuthenticationUrls.put(registration.getClientName(),
                        authorizationRequestBaseUri + "/" + registration.getRegistrationId()));
        model.addAttribute("urls", oauth2AuthenticationUrls);
        return "oauth_login";
    }

    @GetMapping("/")
    public String index(final OAuth2AuthenticationToken authentication, final Model model) {
        model.addAttribute("authorizedClient", this.getAuthorizedClient(authentication));
        return "index";
    }

    @GetMapping("/attr")
    public String userAttributeAtLogin(@AuthenticationPrincipal final OAuth2User oauth2User, final Model model) {
        // NOTE: 認証時点の情報を取得する。
        model.addAttribute("attributes", oauth2User.getAttributes());
        return "userinfo";
    }

    @GetMapping("/attr_latest")
    public String userLatestAttribute(final OAuth2AuthenticationToken authentication, final Model model) {
        // NOTE: 最新の情報を取得する。認証時のアクセストークンを用いてアクセスする。
        final OAuth2AuthorizedClient authorizedClient = this.getAuthorizedClient(authentication);
        final String userInfoUri = authorizedClient.getClientRegistration().getProviderDetails().getUserInfoEndpoint().getUri();
        final RequestEntity<Void> requestEntity = RequestEntity.get(URI.create(userInfoUri))
                .header(HttpHeaders.AUTHORIZATION,"Bearer " + authorizedClient.getAccessToken().getTokenValue())
                .build();
        model.addAttribute("attributes", restTemplate.exchange(requestEntity, Map.class).getBody());
        return "userinfo";
    }

    private OAuth2AuthorizedClient getAuthorizedClient(OAuth2AuthenticationToken authentication) {
        return this.authorizedClientService.loadAuthorizedClient(
                authentication.getAuthorizedClientRegistrationId(), authentication.getName());
    }

}
