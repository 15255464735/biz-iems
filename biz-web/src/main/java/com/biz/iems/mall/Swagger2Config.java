package com.biz.iems.mall;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * 整合swagger2
 */
@Configuration
@EnableSwagger2
public class Swagger2Config{

    /**
     * 设置一个开关，生产版本为false，关闭swagger
     */
    @Value("${swagger.enabled}")
    private boolean ebable;

    @Bean
    public Docket createRestApi(){
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo()).enable(ebable)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.biz.iems.mall"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo(){
        return new ApiInfoBuilder().title("Swagger生成Restful-Api文档")
                .version("1.0.0").build();
    }
}
