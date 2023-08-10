package com.kbds.dgdgtalk.auth.value;

import lombok.Getter;

import java.io.Serializable;

import com.kbds.dgdgtalk.auth.domain.User;

/**
 * <pre>
 * Class Name : SessionUser
 * Description : 세션에 사용자 정보를 저장하기 위한 Dto
 *
 *
 *  Modification Information
 *  Modify Date     Modifier        Comment
 *  -----------------------------------------------
 *  2022. 2. 27.     이수진           new
 *  2022. 3. 03.     김성민         닉네임 추가
 * 
 * </pre>
 *
 * @author d220248(이수진)
 * @since 2022. 2. 27.
 */

@Getter
public class SessionUser implements Serializable {
    private String name;
    private String email;
    private String picture;
    private String nickname;

    public SessionUser(User user) {
        this.name = user.getName();
        this.email = user.getEmail();
        this.picture = user.getPicture();
        this.nickname = user.getNickname();
    }
}
