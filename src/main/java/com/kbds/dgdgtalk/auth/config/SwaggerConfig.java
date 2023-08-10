package com.kbds.dgdgtalk.auth.config;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * <pre>
 * Class Name : Swagger Config
 * Description : Swagger Config
 *
 *
 *  Modification Information
 *  Modify Date     Modifier        Comment
 *  -----------------------------------------------
 *  2022. 3.  4.     허건영           new
 *
 * </pre>
 *
 * @author d220272(이수진)
 * @since 2022. 3. 4.
 */

@Configuration
@EnableSwagger2
@EnableAutoConfiguration
public class SwaggerConfig {
    private static final String API_NAME = "두구두구Talk Project API";
    private static final String API_VERSION = "0.0.1";
    private static final String API_DESCRIPTION = "두구두구Talk API 사용설명서";

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.kbds.dgdgtalk.auth.controller"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title(API_NAME)
                .version(API_VERSION)
                .description(API_DESCRIPTION)
                .contact(new Contact("dgdgtalk", "https://portal.digitalkds.co.kr/", "dgdgtalk@dgdgtalk.com"))
                .build();
    }
}