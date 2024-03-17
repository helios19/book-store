package com.dcs.config;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan({"com.dcs.book.service", "com.dcs.book.model"})
@EnableAutoConfiguration
@EnableCaching
public class TestCacheConfig {
}
