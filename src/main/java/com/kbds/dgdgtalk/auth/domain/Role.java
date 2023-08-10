package com.kbds.dgdgtalk.auth.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;


/**
 * <pre>
 * Class Name : Role
 * Description : Role
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

@Getter
@RequiredArgsConstructor
public enum Role {

    GUEST("ROLE_GUEST", "손님"),
    USER("ROLE_USER", "일반 사용자");

    private final String key;
    private final String title;
}
