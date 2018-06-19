package org.anthem.api.config.swagger;

import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
	 @Bean
	    public Docket api() { 
	        return new Docket(DocumentationType.SWAGGER_2)  
	          .select()   
	          .apis(RequestHandlerSelectors.basePackage("org.anthem.api"))
	          .paths(PathSelectors.any())
	          .build().apiInfo(apiInfo());                                           
	    }
	 //https://github.com/springfox/springfox-demos/blob/master/boot-swagger/src/main/java/springfoxdemo/boot/swagger/Application.java
	 // https://howtodoinjava.com/swagger2/swagger-spring-mvc-rest-example/
	 //https://www.concretepage.com/spring-4/spring-rest-swagger-2-integration-with-annotation-xml-example	 
	 //Swagger access url:  http://localhost:8080/sampleRestful/swagger-ui.html
	  
	 private ApiInfo apiInfo() {
	      return new ApiInfo(
	        "ANTHEM API", 
	        "API for ANTHEM QUIZ UI", 
	        "1.00", 
	        "Terms of service", //terms of service url
	        new Contact("Anthem Inc.", "www.anthem.com", "info@anthem.com"), 
	        "License of API", "API license URL", Collections.emptyList());
	 }
}
