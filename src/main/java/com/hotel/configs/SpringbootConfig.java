package com.hotel.configs;

import java.nio.charset.Charset;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.hotel.interceptors.CheckLoginInterceptor;

@Configuration
public class SpringbootConfig extends WebMvcConfigurerAdapter {
	@Autowired
	private CheckLoginInterceptor checkLoginInterceptor;

	// 注册拦截器
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
//		registry.addInterceptor(checkLoginInterceptor).addPathPatterns("/login");
//		registry.addInterceptor(checkLoginInterceptor).excludePathPatterns("/login");
		// 前台所有界面拦截
		registry.addInterceptor(checkLoginInterceptor).addPathPatterns("/front/*");
		// 管理员所有界面拦截
		registry.addInterceptor(checkLoginInterceptor).addPathPatterns("/administrator/*");
	}
	
	@Bean
    public HttpMessageConverter<String> responseBodyConverter() {
        StringHttpMessageConverter converter = new StringHttpMessageConverter(
                Charset.forName("UTF-8"));
        return converter;
    }

    @Override
    public void configureMessageConverters(
            List<HttpMessageConverter<?>> converters) {
        super.configureMessageConverters(converters);
        converters.add(responseBodyConverter());
    }

    @Override
    public void configureContentNegotiation(
            ContentNegotiationConfigurer configurer) {
        configurer.favorPathExtension(false);
    }
}
