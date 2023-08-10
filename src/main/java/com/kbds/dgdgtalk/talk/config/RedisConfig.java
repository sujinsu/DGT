package com.kbds.dgdgtalk.talk.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * <pre>
 * Class Name : RedisConfig
 * Description : Redis 발행 데이터 확인할 Listener 등록
 *               pub/sub 통신 시 redis 서버와 메시지 교환시 사용할 Serializer 등록
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

@Configuration
public class RedisConfig {

    /**
     * redis pub/sub 메시지 발행(Publish)을 처리하는 listener 설정
     * 
     * @param connectionFactory Redis 서버와의 통신을 위한 low-level 추상화 제공
     * @return
     */
    @Bean
    public RedisMessageListenerContainer redisMessageListener(RedisConnectionFactory connectionFactory) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        return container;
    }


    /**
     * 어플리케이션에서 사용할 redisTemplate 설정
     * 
     * @param connectionFactory                
     * @return redisTemplate    Redis 서버에 command 수행을 위한 high-level 추상화 제공
     */
    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(connectionFactory);

        //redis 서버와 메시지 교환시 사용할 Serializer 등록
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        //Json 편리 >> Jackson2JsonRedisSerializer 
        redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<>(String.class));
        return redisTemplate;
    }

}
