package com.ecom.imgur.client.config;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {

    /**
     * Creates and configures the RestTemplateBuilder bean.
     *
     * @return The configured RestTemplateBuilder bean.
     */
    @Bean
    public RestTemplateBuilder restTemplateBuilder() {
        return new RestTemplateBuilder();
    }

    /**
     * Creates and configures the RestTemplate bean.
     *
     * @param restTemplateBuilder The RestTemplateBuilder instance to build the RestTemplate from.
     * @return The configured RestTemplate bean.
     */

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder restTemplateBuilder) {
        return restTemplateBuilder.build();
    }
}
