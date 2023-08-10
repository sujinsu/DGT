package com.kbds.dgdgtalk.auth.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Builder;

import javax.persistence.*;

/**
 * <pre>
 * Class Name : User
 * Description : User
 *
 *
 *  Modification Information
 *  Modify Date     Modifier        Comment
 *  -----------------------------------------------
 *  2022. 2. 27.     이수진           new
 *  2022. 3.  2.     허건영           name 컬럼 nullable로 변경
 *  2022. 3.  3.     허건영           nickname update 추가
 *
 * </pre>
 *
 * @author d220248(이수진)
 * @since 2022. 2. 27.
 */

@Getter
@NoArgsConstructor
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = true, length = 30)
    private String name;

    @Column(nullable = true, length = 30)
    private String nickname;

    @Column(nullable = false, length = 100)
    private String email;

    @Column(nullable = true, length = 255)
    private String picture;

    @Enumerated(EnumType.STRING) // DB에 저장될 때 문자열로 저장하여 식별
    @Column(nullable = false, length = 10)
    private Role role;


    @Builder
    public User(String name, String email, String nickname, String picture, Role role) {
        this.name = name;
        this.nickname = nickname;
        this.email = email;
        this.picture = picture;
        this.role = role;
    }

    public User update(String name, String picture) {
        this.name = name;
        this.picture = picture;

        return this;
    }

    public void update(String nickname) {
        this.nickname = nickname;
    }

    public String getRoleKey() {
        return this.role.getKey();
    }
}
