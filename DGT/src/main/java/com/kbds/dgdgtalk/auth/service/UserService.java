package com.kbds.dgdgtalk.auth.service;

import com.kbds.dgdgtalk.auth.domain.User;
import com.kbds.dgdgtalk.auth.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;


/**
 * <pre>
 * Class Name : UserService
 * Description : User Service
 *
 *
 *  Modification Information
 *  Modify Date     Modifier        Comment
 *  -----------------------------------------------
 *  2022. 3.  3.     허건영            New
 *
 * </pre>
 *
 * @author d220272(허건영)
 * @since 2022. 3. 3.
 */

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;

    public void update(String email, User userDto) {
        Optional<User> user = userRepository.findByEmail(email);

        user.orElseThrow(() -> new IllegalArgumentException("해당 유저가 없습니다. = " + email));
        user.get().update(userDto.getNickname());
        userRepository.save(user.get());

        user.get().getNickname();
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

}
