package com.kbds.dgdgtalk.talk.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import redis.embedded.RedisServer;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * <pre>
 * Class Name : EmbeddedRedisConfig
 * Description : 내장 Redis를 사용하기 위한 configuration 설정
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
 * 로컬 환경의 경우 내장 Redis 실행
 */
@Profile("local") // Spring 환경 설정
@Configuration
public class EmbeddedRedisConfig {

    @Value("${spring.redis.port}")
    private int redisPort;

    private RedisServer redisServer;

    @PostConstruct // 의존성 주입이 이루어진 후 초기화 수행. 현재는 문제 발생하지 않음.
    public void redisServer() {
        redisServer = new RedisServer(redisPort);
        redisServer.start();
    }

    @PreDestroy // 객체(빈) 제거
    public void stopRedis() {
        if (redisServer != null) {
            redisServer.stop();
        }
    }
}