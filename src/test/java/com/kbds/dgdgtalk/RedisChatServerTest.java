package com.kbds.dgdgtalk;

import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.List;

import com.kbds.dgdgtalk.auth.domain.Role;
import com.kbds.dgdgtalk.auth.domain.User;
import com.kbds.dgdgtalk.auth.repository.UserRepository;
import com.kbds.dgdgtalk.config.TestRedisConfig;
import com.kbds.dgdgtalk.talk.repository.ChatRoomRepository;
import com.kbds.dgdgtalk.talk.value.ChatRoom;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;


import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertNotNull;

/**
 * <pre>
 * Class Name : RedisChatServerTest
 * Description : RedisChatServerTest
 *
 *
 *  Modification Information
 *  Modify Date     Modifier        Comment
 *  -----------------------------------------------
 *  2022. 3.  2.     이수진            New
 *
 * </pre>
 *
 * @author d220248(이수진)
 * @since 2022. 3.  2.
 */

@Slf4j
@WebAppConfiguration
@ContextConfiguration(classes = {TestRedisConfig.class})
@RunWith(SpringRunner.class)
@Transactional
@SpringBootTest
public class RedisChatServerTest {

  @Autowired
  UserRepository userRepository;

  @Autowired
  ChatRoomRepository chatroomrepository;

  
  @Test
  public void Redis_사용자저장() {
 

    User user = User.builder()
      .name("Test")
      .nickname("nickname")
      .email("email")
      .role(Role.USER)
      .build();

    User saved = userRepository.save(user);

    assertNotNull(saved);

  }

  @Test
  public void Redis_채팅방생성(){
    ChatRoom room = chatroomrepository.createChatRoom("TEST ROOM");

    List<ChatRoom> rooms = chatroomrepository.findAllRoom();

    log.info("CREATE ROOM >>>> "+rooms.get(0).getName());

    assertThat(rooms.get(0).getName()).isEqualTo(room.getName());
    
  }


  @Test
  public void Redis_Topic생성확인(){
    
    
    ChatRoom room = chatroomrepository.createChatRoom("TEST ROOM");
    assertNull(chatroomrepository.getTopic(room.getRoomId()));
    
    // Topic 추가
    chatroomrepository.enterChatRoom(room.getRoomId()); 

    log.info("Topic >>>> "+ chatroomrepository.getTopic(room.getRoomId()));
    
    assertNotNull(chatroomrepository.getTopic(room.getRoomId()));
  }

 

}
