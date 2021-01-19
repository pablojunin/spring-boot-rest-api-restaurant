package com.restaurant.demo;

import com.fasterxml.classmate.TypeResolver;

import springfox.documentation.service.Parameter;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.Contact;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.DocExpansion;
import springfox.documentation.swagger.web.ModelRendering;
import springfox.documentation.swagger.web.OperationsSorter;
import springfox.documentation.swagger.web.SecurityConfiguration;
import springfox.documentation.swagger.web.SecurityConfigurationBuilder;
import springfox.documentation.swagger.web.TagsSorter;
import springfox.documentation.swagger.web.UiConfiguration;
import springfox.documentation.swagger.web.UiConfigurationBuilder;

@Configuration
//@Import(BeanValidatorPluginsConfiguration.class)
public class SpringFoxConfig {
	
	  private List<Parameter> globalParameterList() {

		  Parameter authTokenHeader = (Parameter) new ParameterBuilder()
			    .name("AUTH-TOKEN") // name of the header
			    .modelRef(new ModelRef("string")) // data-type of the header
			    .required(true) // required/optional
			    .parameterType("header") // for query-param, this value can be 'query'
			    .description("Basic Auth Token")
			    .build();

		    return Collections.singletonList(authTokenHeader);
		  }
	  
	@Bean
	public Docket api() {
		
		return new Docket(DocumentationType.SWAGGER_2)
				.forCodeGeneration(true)
				.globalOperationParameters(globalParameterList())
				.select()
				.apis(RequestHandlerSelectors.basePackage("com.restaurant.demo.controller"))
				.paths(PathSelectors.any())
				.build()
				.apiInfo(apiInfo());
	}
	
	private ApiInfo apiInfo() {
	    return new ApiInfo(
	      "REST API Sales", 
	      "Restaurant Sales", 
	      "API", 
	      "Terms of service", 
	      new Contact("Pablo Oyarzabal", "restaurant.com", "pablooyarzabal@gmail.com"), 
	      "License of API", "API license URL", Collections.emptyList());
	}
	
    @Autowired
    private TypeResolver typeResolver;

    private ApiKey apiKey() {
      return new ApiKey("mykey", "api_key", "header"); 
    }

    private SecurityContext securityContext() {
      return SecurityContext.builder()
          .securityReferences(defaultAuth())
          .forPaths(PathSelectors.regex("/anyPath.*")) 
          .build();
    }

    List<SecurityReference> defaultAuth() {
      AuthorizationScope authorizationScope
          = new AuthorizationScope("global", "accessEverything");
      AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
      authorizationScopes[0] = authorizationScope;
      return Collections.singletonList(
          new SecurityReference("mykey", authorizationScopes)); 
    }

    @Bean
    SecurityConfiguration security() {
      return SecurityConfigurationBuilder.builder() 
          .clientId("test-app-client-id")
          .clientSecret("test-app-client-secret")
          .realm("test-app-realm")
          .appName("test-app")
          .scopeSeparator(",")
          .additionalQueryStringParams(null)
          .useBasicAuthenticationWithAccessCodeGrant(false)
          .enableCsrfSupport(false)
          .build();
    }

    @Bean
    UiConfiguration uiConfig() {
      return UiConfigurationBuilder.builder() 
          .deepLinking(true)
          .displayOperationId(false)
          .defaultModelsExpandDepth(1)
          .defaultModelExpandDepth(1)
          .defaultModelRendering(ModelRendering.EXAMPLE)
          .displayRequestDuration(false)
          .docExpansion(DocExpansion.NONE)
          .filter(false)
          .maxDisplayedTags(null)
          .operationsSorter(OperationsSorter.ALPHA)
          .showExtensions(false)
          .showCommonExtensions(false)
          .tagsSorter(TagsSorter.ALPHA)
          .supportedSubmitMethods(UiConfiguration.Constants.DEFAULT_SUBMIT_METHODS)
          .validatorUrl(null)
          .build();
    }

    
}
