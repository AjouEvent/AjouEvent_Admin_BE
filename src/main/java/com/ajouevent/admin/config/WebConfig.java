package com.ajouevent.admin.config;

import com.ajouevent.admin.auth.AdminAuthCheckFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final AdminAuthCheckFilter adminAuthCheckFilter;

    public WebConfig(AdminAuthCheckFilter adminAuthCheckFilter) {
        this.adminAuthCheckFilter = adminAuthCheckFilter;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
    }

    @Bean
    public FilterRegistrationBean<AdminAuthCheckFilter> adminAuthFilterRegistration() {
        FilterRegistrationBean<AdminAuthCheckFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(adminAuthCheckFilter);
        registrationBean.addUrlPatterns("/api/admin/*", "/api/admin"); //admin/뒤에 오는 모든 요청은 자동으로 세션 검증을 진행함.
        registrationBean.setOrder(1); // 필터 우선순위 지정 (낮을수록 먼저 실행)
        return registrationBean;
    }
}
