package com.kbds.dgdgtalk.auth.controller;

import lombok.RequiredArgsConstructor;

import org.springframework.core.ResolvableType;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.HashMap;
import java.util.Map;

import com.kbds.dgdgtalk.auth.util.LoginUser;
import com.kbds.dgdgtalk.auth.value.SessionUser;

/**
 * <pre>
 * Class Name : LoginController
 * Description : LoginController
 *
 *
 *  Modification Information
 *  Modify Date     Modifier        Comment
 *  -----------------------------------------------
 *  2022. 3.  2.     이수진           new
 *  2022. 3.  3.     김성민    닉네임 변경 페이지 추가
 *  2022. 3.  6.     허건영    채팅 프로필 이미지 추가
 * 
 * </pre>
 *
 * @author d220248(이수진)
 * @since 2022. 3.  2.
 */

@RequiredArgsConstructor
@Controller
public class LoginController {
    
    // authorizationRequestBaseUri : 인증 페이지 기본 URL
    private static final String authorizationRequestBaseUri = "oauth2/authorization";
    // oauth2AuthenticationUrls - key : 소셜 사이트, value : url 경로
    Map<String, String> oauth2AuthenticationUrls = new HashMap<>();
    // ClientRegistrationRepository : registrationId로 식별된 클라이언트 반환 
    private final ClientRegistrationRepository clientRegistrationRepository;

    /**
     * 
     * @param model         MVC 중 M
     * @param sessionUser   name, email, picture 를 가진 세션 사용자
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked") // 검증되지 않은 연산자 관련 경고 억제
    @GetMapping(value = {"", "/", "/login"})
    public String getLoginPage(Model model , @LoginUser SessionUser sessionUser) throws Exception {
        
        if (sessionUser != null) {
            model.addAttribute("userName", sessionUser.getName());
        }
        
        Iterable<ClientRegistration> clientRegistrations = null;

        // ResolvableType:  AbstractMap<Integer, List<String>>
        ResolvableType type = ResolvableType.forInstance(clientRegistrationRepository)
                .as(Iterable.class);
        if (type != ResolvableType.NONE &&
                ClientRegistration.class.isAssignableFrom(type.resolveGenerics()[0])) {
            clientRegistrations = (Iterable<ClientRegistration>) clientRegistrationRepository;
        }

        assert clientRegistrations != null;
        clientRegistrations.forEach(registration ->
                oauth2AuthenticationUrls.put(registration.getClientName(),
                        authorizationRequestBaseUri + "/" + registration.getRegistrationId()));
        model.addAttribute("urls", oauth2AuthenticationUrls);
 
        return "auth/oauth-login";
    }

    @GetMapping(value="/nick")
    public String nickChange(Model model, @LoginUser SessionUser sessionUser) throws Exception {
        if (sessionUser == null) {
            return "login";
        }

        model.addAttribute("sessionUser", sessionUser); // 프로필 이미지 출력을 위해 sessionUser로 변경
        return "auth/nickname-input";
    }
}