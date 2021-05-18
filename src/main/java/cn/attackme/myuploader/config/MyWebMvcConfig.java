package cn.attackme.myuploader.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Author: georgexie
 * @description: TODO
 * @Date: 2021/5/18 10:20 上午
 * @Version 1.0
 */

@Configuration
public class MyWebMvcConfig implements WebMvcConfigurer {
	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/File/**"); //对应的接口
			}
		};
	}
}

