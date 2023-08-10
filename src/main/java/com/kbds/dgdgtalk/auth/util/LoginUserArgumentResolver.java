package com.kbds.dgdgtalk.auth.util;

import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpSession;

import com.kbds.dgdgtalk.auth.value.SessionUser;

/**
 * <pre>
 * Class Name : LoginUserArgumentResolver
 * Description : LoginUserArgumentResolver
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
@Component
public class LoginUserArgumentResolver implements HandlerMethodArgumentResolver {
    private final HttpSession httpSession;

    /**
     * 컨트롤러 메서드의 특정 파라미터를 지원하는지 판단
     * >> 파라미터에 LoginUser 어노테이션 && 세션유저 클래스면 true
     */
    @Override
    public boolean supportsParameter(MethodParameter parameter) { 
        boolean isLoginUserAnnotation = parameter.getParameterAnnotation(LoginUser.class) != null;
        boolean isUserClass = SessionUser.class.equals(parameter.getParameterType());

        return isLoginUserAnnotation && isUserClass;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        // 파라미터에 전달할 객체 생성 (여기서는 세션에서 객체를 가져옴)
        return httpSession.getAttribute("user"); 
    }
}
