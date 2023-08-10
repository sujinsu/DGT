package com.kbds.dgdgtalk.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

import com.kbds.dgdgtalk.auth.domain.User;


/**
 * <pre>
 * Interface Name : UserRepository
 * Description : UserRepository
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

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}

