package com.makar.tenant.security;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {

    @Bean
    public FilterRegistrationBean<TenantNameFilter> yourCustomFilterRegistration(TenantNameFilter filter) {
        FilterRegistrationBean<TenantNameFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(filter);
        // Set order to execute before Spring Security (default -100)
        registrationBean.setOrder(-101);
        return registrationBean;
    }
}
