package com.ssafy.trip.config;

import static springfox.documentation.builders.PathSelectors.regex;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
// @EnableSwagger2 // 3.0부터는 사용하지 않아도 됨
public class SwaggerConfiguration {

//	Swagger-UI 2.x 확인
//	http://localhost:8080/{your-app-root}/swagger-ui.html
//	Swagger-UI 3.x 확인
//	http://localhost:8080/{your-app-root}/swagger-ui/index.html

	private String version = "V1";
	private String title = "EnjoyTrip API " + version;
	
	@Bean
	public Docket api() {
		
//		// 헤더 값 목록
//        List<Parameter> globalParamters = new ArrayList<>();
//        globalParamters.add(new ParameterBuilder().name("Authorization").
//				  description("Access Token").parameterType("header").
//				  required(false).modelRef(new ModelRef("string")).build());
//        globalParamters.add(new ParameterBuilder().name("Authorization").
//				  description("Refresh Token").parameterType("header").
//				  required(false).modelRef(new ModelRef("string")).build());
        
		return new Docket(DocumentationType.SWAGGER_2).consumes(getConsumeContentTypes()).produces(getProduceContentTypes())
					.apiInfo(apiInfo()).groupName(version).select()
					.apis(RequestHandlerSelectors.basePackage("com.ssafy.trip"))
					.paths(regex("/(user|board|map|plan)/*.*")).build()
					.useDefaultResponseMessages(false);
	}
	
	// request
	private Set<String> getConsumeContentTypes() {
        Set<String> consumes = new HashSet<>();
        consumes.add("application/json;charset=UTF-8");
//      consumes.add("application/xml;charset=UTF-8");
        consumes.add("application/x-www-form-urlencoded");
        return consumes;
    }

	// response
    private Set<String> getProduceContentTypes() {
        Set<String> produces = new HashSet<>();
        produces.add("application/json;charset=UTF-8");
        return produces;
    }

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder().title(title)
				.description("<h3>EnjoyTrip API Reference for Developers</h3>Swagger를 이용한 EnjoyTrip API<br><img src=\"/assets/img/ssafy_logo.png\" width=\"150\">") 
				.contact(new Contact("SSAFY", "https://edu.ssafy.com", "ssafy@ssafy.com"))
				.license("SSAFY License")
				.licenseUrl("https://www.ssafy.com/ksp/jsp/swp/etc/swpPrivacy.jsp")
				.version("1.0").build();
	}
	
	// 버튼 클릭 시 입력 값 설정
    private ApiKey apiKey(){
        return new ApiKey("Authorization", "Bearer", "header");
    }
	
}
