package com.ajouevent.admin.config;

import com.ajouevent.admin.auth.AdminAuthCheckFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.session.web.http.HttpSessionIdResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.session.web.http.HeaderHttpSessionIdResolver;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final AdminAuthCheckFilter adminAuthCheckFilter;

    public WebConfig(AdminAuthCheckFilter adminAuthCheckFilter) {
        this.adminAuthCheckFilter = adminAuthCheckFilter;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
//                .allowedOrigins("https://43.200.133.31")
                .allowedOrigins("http://localhost:5173")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH")
                .allowedHeaders("*")
                .exposedHeaders("X-Session-Id")
                .allowCredentials(true); // 쿠키 포함 허용!
    }

    //react 정적으로 때렸을 때, dom 오류 방지
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        // [a-zA-Z0-9-_]+ 1단계 경로 매핑
        registry.addViewController("/{spring:[a-zA-Z0-9-_]+}")
                .setViewName("forward:/index.html");
        // 2단계 경로 매핑
        registry.addViewController("/{spring:[a-zA-Z0-9-_]+}/{spring2:[a-zA-Z0-9-_]+}")
                .setViewName("forward:/index.html");
        // 3단계 경로 매핑
        registry.addViewController("/{spring:[a-zA-Z0-9-_]+}/{spring2:[a-zA-Z0-9-_]+}/{spring3:[a-zA-Z0-9-_]+}")
                .setViewName("forward:/index.html");
    }


    @Bean
    public FilterRegistrationBean<AdminAuthCheckFilter> adminAuthFilterRegistration() {
        FilterRegistrationBean<AdminAuthCheckFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(adminAuthCheckFilter);
        registrationBean.addUrlPatterns("/api/admin/*", "/api/admin"); //admin/뒤에 오는 모든 요청은 자동으로 세션 검증을 진행함.
        registrationBean.setOrder(1); // 필터 우선순위 지정 (낮을수록 먼저 실행)
        return registrationBean;
    }

    @Bean
    public HttpSessionIdResolver httpSessionIdResolver() {
        // "X-Session-Id" 헤더만 쓰도록 지정
        return new HeaderHttpSessionIdResolver("X-Session-Id");
    }
}
