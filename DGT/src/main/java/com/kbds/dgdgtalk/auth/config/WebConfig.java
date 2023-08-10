package com.kbds.dgdgtalk.auth.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

import com.kbds.dgdgtalk.auth.util.LoginUserArgumentResolver;

/**
 * <pre>
 * Class Name : WebConfig
 * Description : WebConfig
 *
 *
 *  Modification Information
 *  Modify Date     Modifier        Comment
 *  -----------------------------------------------
 *  2022. 2. 27.     이수진           new
 *
 * </pre>
 *
 * @author d220248(이수진)
 * @since 2022. 2. 27.
 */

@RequiredArgsConstructor
@Configuration
public class WebConfig implements WebMvcConfigurer {
    private final LoginUserArgumentResolver loginUserArgumentResolver;

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolverList) {
        argumentResolverList.add(loginUserArgumentResolver);
    }
}
