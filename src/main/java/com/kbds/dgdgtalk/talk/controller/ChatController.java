package com.kbds.dgdgtalk.talk.controller;

import com.kbds.dgdgtalk.talk.redis.RedisPublisher;
import com.kbds.dgdgtalk.talk.repository.ChatRoomRepository;
import com.kbds.dgdgtalk.talk.value.ChatMessage;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

/**
 * <pre>
 * Class Name : ChatController
 * Description : 채팅 컨트롤러
 *
 *
 *  Modification Information
 *  Modify Date     Modifier        Comment
 *  -----------------------------------------------
 *  2022. 2. 21.     김성민            New
 *  2022. 2. 22.     김성민    Add Stomp with pub/sub
 *  2022. 2. 22.     이수진    Add Redis
 *
 * </pre>
 *
 * @author d220159(김성민)
 * @since 2022. 2. 21.
 */

@RequiredArgsConstructor
@Controller
public class ChatController {

    private final RedisPublisher redisPublisher;
    private final ChatRoomRepository chatRoomRepository;

    /**
     * websocket "/pub/chat/message"로 들어오는 메시징을 처리한다.
     */
    @MessageMapping("/chat/message")
    public void message(ChatMessage message) {
        if (ChatMessage.MessageType.ENTER.equals(message.getType())) {
            // 클라이언트 입장시 채팅방(Topic) 대화 가능을 위해 리스너 연동
            chatRoomRepository.enterChatRoom(message.getRoomId());
            message.setMessage(message.getSender() + "님이 입장하셨습니다.");
        }
        // Websocket에 발행된 메시지를 redis로 발행한다 (publish)
        redisPublisher.publish(chatRoomRepository.getTopic(message.getRoomId()), message);
    }
}