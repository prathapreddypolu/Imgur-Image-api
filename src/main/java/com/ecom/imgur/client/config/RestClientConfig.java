package com.ecom.imgur.client.config;

import org.springframework.boot.web.client.ClientHttpRequestFactories;
import org.springframework.boot.web.client.ClientHttpRequestFactorySettings;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.DefaultUriBuilderFactory;

@Configuration
public class RestClientConfig {

    /**
     * Creates and configures the RestClient bean.
     *
     * @return The configured RestClient bean.
     */
    @Bean
    public RestClient restClient() {
        DefaultUriBuilderFactory defaultUriBuilderFactory = new DefaultUriBuilderFactory();
        defaultUriBuilderFactory.setEncodingMode(DefaultUriBuilderFactory.EncodingMode.VALUES_ONLY);
        ClientHttpRequestFactorySettings factorySettings = ClientHttpRequestFactorySettings.DEFAULTS;
        ClientHttpRequestFactory requestFactory = ClientHttpRequestFactories.get(factorySettings);
        return RestClient.builder().uriBuilderFactory(defaultUriBuilderFactory).requestFactory(requestFactory).build();
    }
}