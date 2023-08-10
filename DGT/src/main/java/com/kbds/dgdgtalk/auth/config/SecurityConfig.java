package com.kbds.dgdgtalk.auth.config;

import lombok.RequiredArgsConstructor;

import com.kbds.dgdgtalk.auth.service.CustomOAuth2UserService;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * <pre>
 * Class Name : SecurityConfig
 * Description : SecurityConfig
 *
 *
 *  Modification Information
 *  Modify Date     Modifier        Comment
 *  -----------------------------------------------
 *  2022. 2. 27.     이수진           new
 *  2022. 3.  2.     이수진     권한 관리 대상 추가(antMatchers)
 *  2022. 3.  3.     김성민     로그인 성공 후 redirect 페이지 변경
 *  2022. 3.  4.     허건영     Swagger 접근 url 추가
 *
 * </pre>
 *
 * @author d220248(이수진)
 * @since 2022. 2. 27.
 */

@RequiredArgsConstructor
@EnableWebSecurity // Spring Security 설정 활성화
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final CustomOAuth2UserService customOAuth2UserService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // h2-console 화면 사용하기 위해 옵션 disable
        http.csrf().disable().headers().frameOptions().disable() 
                .and()
                    // authorizeRequests : URL별 권한 권리를 설정하는 옵션 시작점
                    .authorizeRequests() 
                    // antMatchers : 권한 관리 대상 지정 옵션
                    .antMatchers("/", "/css/**", "/images/***",
                        // permitAll : 전체 권한 열람 옵션
                        "/js/**", "/static/**", "/h2-console/**",
                        "/users/**", "/swagger*/**", "/swagger-resources/**",
                        "/webjars/**", "swagger-ui.html").permitAll()
                    // USER권한을 가진 사람만 접근 가능
                    .antMatchers("/login").permitAll()
                    // .antMatchers("/chat/room/**").hasRole(Role.USER.name()) 
                    // anyRequest : 설정한 값 이외 나머지 URL
                    // authenticated : 인증된 사용자들에게만 허용
                    .anyRequest().authenticated() 
                .and()
                    // 로그아웃 기능에 대한 여러 설정의 진입점
                    .logout() 
                        .logoutSuccessUrl("/login")
                .and()
                    // oauth2 로그인 기능에 대한 여러 설정의 진입점
                    .oauth2Login()
                    .loginPage("/login")
                        .defaultSuccessUrl("/nick")
                        // Oauth 2 로그인 성공 이후 사용자 정보를 가져올 때 설정 담당
                        .userInfoEndpoint()
                // 소셜 로그인 성공 이후 후속 조치를 진행할 UserService 인터페이스의 구현체 등록
                .userService(customOAuth2UserService); 
    }
    
}
