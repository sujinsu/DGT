package com.kbds.dgdgtalk.talk.redis;

import lombok.RequiredArgsConstructor;

import com.kbds.dgdgtalk.talk.value.ChatMessage;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Service;

/**
 * <pre>
 * Class Name : RedisPublisher
 * Description : 메시지를 Redis Publisher 서버로 발행
 *
 *
 *  Modification Information
 *  Modify Date     Modifier        Comment
 *  -----------------------------------------------
 *  2022. 2. 22.     이수진            New
 *  2022. 2. 27.     이수진            주석 추가
 *
 * </pre>
 *
 * @author d220248(이수진)
 * @since 2022. 2. 22.
 */

 /**
  * redis template 주입 & 발행
  */
@RequiredArgsConstructor
@Service
public class RedisPublisher {
    private final RedisTemplate<String, Object> redisTemplate;

    public void publish(ChannelTopic topic, ChatMessage message) {
        redisTemplate.convertAndSend(topic.getTopic(), message);

    }
}
