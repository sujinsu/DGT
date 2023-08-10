package com.kbds.dgdgtalk.talk.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;


/**
 * <pre>
 * Class Name : WebSocketConfig
 * Description : 웹 소켓 Configuration
 *
 *
 *  Modification Information
 *  Modify Date     Modifier        Comment
 *  -----------------------------------------------
 *  2022. 2. 21.     김성민            New
 *  2022. 2. 22.     김성민    Add Stomp with pub/sub
 *
 * </pre>
 *
 * @author d220159(김성민)
 * @since 2022. 2. 21.
 */

/**
 * @EnableWebSocketMessageBroker 어노테이션을 통해 Configuration에 websocket 서버를 사용한다고 설정해준 뒤
 *  webSocketMessageBrokerConfigurer를 상속받아 몇몇 메소드를 구현하여 websocket 연결 속성을 설정함
 */
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/sub");//메시지 구독 요청 prefix를 "/sub"로 시작하도록 설정
        config.setApplicationDestinationPrefixes("/pub");//메시지 발행 요청 prefix "/pub"
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry stompEndpointRegistry) {
        stompEndpointRegistry.addEndpoint("/ws-stomp").setAllowedOrigins("*").withSockJS();//stomp websocket의 연결 endpoint 설정
    }
}
