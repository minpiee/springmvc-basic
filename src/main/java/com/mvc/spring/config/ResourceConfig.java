package com.mvc.spring.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class ResourceConfig implements WebMvcConfigurer {

	@Value("${uploadDir}")
	private String uploadDir;

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		String location = "file:///" + (uploadDir.endsWith("/") ? uploadDir : uploadDir + "/");

		// /images/로 시작하는 주소로 요청이 오면
		registry.addResourceHandler("/images/**")
				// C:/upload/ 디렉터리에서 파일을 찾는다
				.addResourceLocations(location)
				// 브라우저가 이미지를 최대 1년 동안 캐시하도록 설정 (선택 사항)
				.setCachePeriod(60 * 60 * 24 * 365);
	}
}