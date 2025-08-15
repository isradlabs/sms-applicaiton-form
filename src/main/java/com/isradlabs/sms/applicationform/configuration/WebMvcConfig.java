package com.isradlabs.sms.applicationform.configuration;
import java.util.List;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.multipart.support.MultipartFilter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableSpringDataWebSupport
public class WebMvcConfig implements WebMvcConfigurer {
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add( new PageableHandlerMethodArgumentResolver());
    }
    /*
    @Bean
    public MultipartResolver multipartResolver() {
        CommonsMultipartResolver resolver = new CommonsMultipartResolver();
        //100MB
        resolver.setMaxUploadSize(100 * (long) 1024 * 1024);
        return resolver;
    }

    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        factory.setMaxFileSize(DataSize.ofMegabytes(200L));
        factory.setMaxRequestSize(DataSize.ofMegabytes(200L));
        return factory.createMultipartConfig();
    }
    */
    private static final String DEFAULT_SPRING_5_MULTIPART_RESOLVER_BEAN_NAME = "multipartResolver";
    public static final String REST_PATH = "/v1/*";
/*
    @Bean
    @Order(0)
    public FilterRegistrationBean<MultipartFilter> multipartFilter() {
            FilterRegistrationBean<MultipartFilter> registrationBean = new FilterRegistrationBean<>();
            MultipartFilter multipartFilter = new MultipartFilter();
            multipartFilter.setMultipartResolverBeanName(DEFAULT_SPRING_5_MULTIPART_RESOLVER_BEAN_NAME);

            registrationBean.setFilter(multipartFilter);
            registrationBean.setOrder(0);
            registrationBean.addUrlPatterns(REST_PATH);
            return registrationBean;
    }
    */
}