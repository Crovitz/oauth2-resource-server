package com.example.oauth2resourceserver.configuration;

import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.RemoteTokenServices;

@Configuration
@EnableResourceServer
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true, jsr250Enabled = true)
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {
    private final ResourceServerProperties resourceServerProperties;
    private final CustomAccessTokenConverter customAccessTokenConverter;

    public ResourceServerConfiguration(ResourceServerProperties resourceServerProperties, CustomAccessTokenConverter customAccessTokenConverter) {
        this.resourceServerProperties = resourceServerProperties;
        this.customAccessTokenConverter = customAccessTokenConverter;
    }

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) {
        resources.tokenServices(customTokenServices());
    }

    private RemoteTokenServices customTokenServices() {
        RemoteTokenServices services = new RemoteTokenServices();
        services.setAccessTokenConverter(this.customAccessTokenConverter);
        services.setCheckTokenEndpointUrl(this.resourceServerProperties.getTokenInfoUri());
        services.setClientId(this.resourceServerProperties.getClientId());
        services.setClientSecret(this.resourceServerProperties.getClientSecret());
        return services;
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/public").permitAll()
                .anyRequest().authenticated();
    }
}
