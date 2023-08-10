package com.kbds.dgdgtalk.auth.value;

import lombok.Builder;
import lombok.Getter;

import java.util.Map;

import com.kbds.dgdgtalk.auth.domain.Role;
import com.kbds.dgdgtalk.auth.domain.User;

/**
 * <pre>
 * Class Name : OAuthAttributes
 * Description : 가입시점의 OAuthAttributes Dto
 *
 *
 *  Modification Information
 *  Modify Date     Modifier        Comment
 *  -----------------------------------------------
 *  2022. 2. 27.     이수진           new
 *  2022. 2. 28.     김성민           naver 생성자, naver 판단 코드 추가
 *  2022. 3.  2.     허건영           kakao 생성자, kakao 판단 코드 추가
 *
 * </pre>
 *
 * @author d220248(이수진)
 * @since 2022. 2. 27.
 */

@Getter
public class OAuthAttributes {
    private Map<String, Object> attributes;
    private String nameAttributeKey;
    private String name;
    private String email;
    private String nickname;
    private String picture;

    @Builder
    public OAuthAttributes(Map<String, Object> attributes, String nameAttributeKey, String name, String email, String nickname, String picture) {
        this.attributes = attributes;
        this.nameAttributeKey = nameAttributeKey;
        this.name = name;
        this.email = email;
        this.nickname = nickname;
        this.picture = picture;
    }

    // of : OAuth2User에서 반환하는 사용자 정보는 Map이라 하나하나 값을 변환
    public static OAuthAttributes of(String registrationId, String userNameAttributeName, Map<String, Object> attributes) {
        if ("naver".equals(registrationId)) { //registrationId가 naver일 경우 naver 생성자 호출
            return ofNaver("id", attributes);
        } else if("kakao".equals(registrationId)) {
            return ofKakao("id", attributes);
        }

        return ofGoogle(userNameAttributeName, attributes);
    }

    private static OAuthAttributes ofGoogle(String userNameAttributeName, Map<String, Object> attributes) {
        return OAuthAttributes.builder()
                .name((String) attributes.get("name"))
                .email((String) attributes.get("email"))
                .picture((String) attributes.get("picture"))
                .attributes(attributes)
                .nameAttributeKey(userNameAttributeName)
                .build();
    }

    private static OAuthAttributes ofNaver(String userNameAttributeName, Map<String, Object> attributes) {
        Map<String,Object> response = (Map<String, Object>) attributes.get("response");

        return OAuthAttributes.builder()
                .name((String) response.get("name"))
                .email((String) response.get("email"))
                .picture((String) response.get("profile_image"))
                .attributes(response)
                .nameAttributeKey(userNameAttributeName)
                .build();
    }

    private static OAuthAttributes ofKakao(String userNameAttributeName, Map<String, Object> attributes) {
        Map<String,Object> properties = (Map<String, Object>) attributes.get("properties"); // 닉네임, 프로필 사진
        Map<String,Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account"); // 이메일

        return OAuthAttributes.builder()
                .email((String) kakaoAccount.get("email"))
                .nickname((String) properties.get("nickname"))
                .picture((String) properties.get("profile_image"))
                .attributes(attributes)
                .nameAttributeKey(userNameAttributeName)
                .build();
    }

    // User Entity 생성 (가입시)
    public User toEntity() {
        return User.builder()
                .name(name)
                .email(email)
                .nickname(nickname)
                .picture(picture)
                .role(Role.GUEST)
                .build();
    }
}
