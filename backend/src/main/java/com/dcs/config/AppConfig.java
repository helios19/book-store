package com.dcs.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class loading up application properties.
 */
@Configuration
@EnableConfigurationProperties
//@ConfigurationProperties(prefix = "spring.data.mongodb")
@Getter @Setter
public class AppConfig {

    private String host;
    private String port;
    private String database;
    private String uri;

}
