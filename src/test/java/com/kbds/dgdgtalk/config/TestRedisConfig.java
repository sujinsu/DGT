package com.kbds.dgdgtalk.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.TestPropertySource;

import redis.embedded.RedisServer;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.stream.Collectors;

/**
 * <pre>
 * Class Name : TestRedisConfig
 * Description : TestRedisConfig
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
 * @since 2022. 3. 2.
 */


@Slf4j
// @Profile({"local", "dev", "test"}) Runtime 실행 시 프로파일을 꼭 추가해야한다.
@TestPropertySource("classpath:application-test.yml")
// @TestPropertySource(properties = { "spring.config.location=classpath:application-test.yml" })
// @PropertySource("classpath:application-test.yml")
@TestConfiguration
public class TestRedisConfig {

    private RedisServer redisServer;

    public TestRedisConfig(){
        redisServer = new RedisServer(16379);
        log.info("Embedded Redis Object Created");
    }

    // public TestRedisConfig(@Value("${spring.testredis.port}") int redisPort) {
    //     redisServer = new RedisServer(redisPort);
    //     log.info("Embedded Redis Object Created");
    // }

    @PostConstruct // 의존성 주입이 이루어진 후 초기화 수행. 현재는 문제 발생하지 않음.
    public void startRedis() {
        redisServer.start();
        log.info("Embedded Redis Started");
        log.info(" >> Port: " + redisServer.ports().stream().map(n -> String.valueOf(n)).collect(Collectors.joining()));
    }

    @PreDestroy // 객체(빈) 제거
    public void stopRedis() {
        redisServer.stop();
        log.info("Embedded Redis Stopped");
    }

}