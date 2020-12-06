package com.simple.banking.eterationdemo.config;

import com.google.common.base.Predicate;
import springfox.documentation.service.ApiInfo;
import org.springframework.context.annotation.Bean;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.spring.web.plugins.Docket;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@Configuration
public class SwaggerConfiguration {
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("All Services").select()
                .apis(RequestHandlerSelectors.basePackage("com.simple.banking.eterationdemo"))
                .paths(allPaths())
                .build()
                .apiInfo((apiInfo()));
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("REST API")
                .description("REST API Documentation")
                .version("1.0")
                .build();
    }

    private Predicate<String> allPaths() {
        return PathSelectors.any();
    }
}
