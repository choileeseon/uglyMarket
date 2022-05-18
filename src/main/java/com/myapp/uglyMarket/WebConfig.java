package com.myapp.uglyMarket;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;



@Configuration
public class WebConfig implements WebMvcConfigurer{
	
//	@Override
//	public void addViewControllers(ViewControllerRegistry registry) {
//		registry.addViewController("/").setViewName("home");
//	}
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		// 저장된 파일(이미지)의 경로를 지정한다. (이미지를 사용하기 위함)
		registry
			.addResourceHandler(".media/**")
			.addResourceLocations("C://SPRINGBOOT//Spring-workspace//uglyMarket//src//main//resources//static//media");
	}
}
