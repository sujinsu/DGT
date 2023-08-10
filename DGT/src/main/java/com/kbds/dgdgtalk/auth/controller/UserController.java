package com.kbds.dgdgtalk.auth.controller;

import com.kbds.dgdgtalk.auth.domain.User;
import com.kbds.dgdgtalk.auth.service.UserService;
import com.kbds.dgdgtalk.auth.util.LoginUser;
import com.kbds.dgdgtalk.auth.value.SessionUser;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <pre>
 * Class Name : UserController
 * Description : User Controller
 *
 *
 *  Modification Information
 *  Modify Date     Modifier        Comment
 *  -----------------------------------------------
 *  2022. 3.  3.     허건영            New
 *  2022. 3.  4.     허건영         Swagger 적용
 *
 * </pre>
 *
 * @author d220272(허건영)
 * @since 2022. 3. 3.
 */

@Api(tags = {"회원 관리 API"})
@RequiredArgsConstructor
@RequestMapping("/users")
@RestController
public class UserController {
    private final UserService userService;

    @ApiOperation(value = "회원 nickname update")
    @ApiImplicitParam(name = "별명 설정", value = "회원 별명 업데이트")
    @PutMapping("/nickname")
    public void update(@LoginUser SessionUser sessionUser, @RequestBody User userDto) {
        String nickName = userDto.getNickname();

        if (nickName.equals("") || nickName.length() == 0) {
            return;
        }

        userService.update(sessionUser.getEmail(), userDto);
    }

}
