package com.dcs.config;

import com.dcs.common.security.XssFilter;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.WebProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


/**
 * Configuration class enabling web filers such as {@link XssFilter} and redirecting root url calls
 * to {@code index.html} file.
 */
@EnableSpringDataWebSupport
@Configuration
@EnableConfigurationProperties({WebProperties.class})
@Slf4j
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private WebProperties resourceProperties = new WebProperties();
    private static final String[] CLASSPATH_RESOURCE_LOCATIONS = {
            "classpath:/META-INF/resources/", "classpath:/resources/",
            "classpath:/static/", "classpath:/public/" };
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        Integer cachePeriod = 3600;//resourceProperties.getCachePeriod();

        if (!registry.hasMappingForPattern("/**")) {
            registry.addResourceHandler("/**").addResourceLocations(
                    CLASSPATH_RESOURCE_LOCATIONS).setCachePeriod(cachePeriod);;
        }
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("forward:/index.html");
    }

    /**
     * Returns an {@link FilterRegistrationBean} instance wrapping a {@link XssFilter} filter
     * to be loaded by the spring context.
     *
     * @return FilterRegistrationBean instance
     */
//    @Bean
    public FilterRegistrationBean xssFilterRegistrationBean() {
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        XssFilter xssFilter = new XssFilter();
        registrationBean.setFilter(xssFilter);
        registrationBean.setOrder(1);
        return registrationBean;
    }

    /**
     * Returns a Jackson message converter customized with the following parameters:
     * <li> - {@link DeserializationFeature#FAIL_ON_UNKNOWN_PROPERTIES} disabled
     * <li> - {@link SerializationFeature#WRITE_DATES_AS_TIMESTAMPS} disabled
     *
     * @return MappingJackson2HttpMessageConverter
     */
    @Bean
    @Primary
    public MappingJackson2HttpMessageConverter jacksonConvertor() {
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        converter.setObjectMapper(mapper);
        return converter;
    }
}
