package com.hotel;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.HttpMessageConverters;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.web.servlet.ErrorPage;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;

@SpringBootApplication// 标记当前类为springboot的启动类
// @RestController ----@Controller+@requestMapping
@Controller
@MapperScan("com.hotel.*")//注解所在的包

public class SpringBootApplicationStart {
	public static void main(String[] args) {
		System.out.println("开始...myDemo1");
		SpringApplication.run(SpringBootApplicationStart.class, args);
		System.out.println("结束...myDemo1");
	}
	@RequestMapping(value = "/init")
	@ResponseBody
	public String init() {
		System.out.println("init...");
		return "hello,springboot";
	}
	
	
	// 配置错误页面---Lambda表达式
	@Bean
	public EmbeddedServletContainerCustomizer containerCustomizer() {
		return (container -> {
			ErrorPage error401Page = new ErrorPage(HttpStatus.UNAUTHORIZED, "/401.html");
			ErrorPage error404Page = new ErrorPage(HttpStatus.NOT_FOUND, "/404.html");
			ErrorPage error500Page = new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/500.html");
			container.addErrorPages(error401Page, error404Page, error500Page);
		});
	}
	
	@Bean
	public HttpMessageConverters fastJsonHttpMessageConverters() {
		System.out.println("fastjson.....转换器");
		FastJsonHttpMessageConverter mc = new FastJsonHttpMessageConverter();
		FastJsonConfig fjc = new FastJsonConfig();
		fjc.setSerializerFeatures(SerializerFeature.PrettyFormat);
		mc.setFastJsonConfig(fjc);
		HttpMessageConverter<?> converter = mc;
		return new HttpMessageConverters(converter);
	}

}
