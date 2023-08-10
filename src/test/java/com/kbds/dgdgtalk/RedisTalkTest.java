package com.kbds.dgdgtalk;

import java.net.URI;
import java.time.Duration;
import java.util.concurrent.atomic.AtomicInteger;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.socket.client.WebSocketClient;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)//테스트 시 랜덤 포트 사용
public class RedisTalkTest {
    @Autowired
	private ApplicationContext applicationContext;

    @Autowired
	private ObjectMapper objectMapper;
	private WebTestClient testClient;

    @LocalServerPort
	private int port;

    private final String message = "Hello World";
	private final String messageResponse = "Message Sent Successfully!.";

    @Before
	void setupWebClient() {
		testClient = WebTestClient.bindToApplicationContext(applicationContext).build();
	}

    @Test
	void testPostMessage() {

		String webSocketConnectionUrl = "http://localhost:" + port + "/sub/chat/room/";
		AtomicInteger messageCounter = new AtomicInteger(0);
		int messageCount = 5;

		/*WebSocketClient webSocketClient = new ReactorNettyWebSocketClient();
		/Mono<Void> executeWebSocketConnectMono = webSocketClient.execute(URI.create(webSocketConnectionUrl),
			session -> session.receive()
				.doOnNext(webSocketMessage -> {
					try {
						log.info("Message Received in Test Connection: {}", webSocketMessage.getPayloadAsText());
						ChatMessage chatMessage = objectMapper.readValue(webSocketMessage.getPayloadAsText(), ChatMessage.class);
						assertThat(chatMessage.getMessage()).isEqualTo(message);
						assertThat(chatMessage.getId()).isEqualTo(messageCounter.incrementAndGet());
					} catch (JsonProcessingException e) {
						throw new RuntimeException(e);
					}
				})
				.then(session.close())
		).timeout(Duration.ofSeconds(5)); // Assuming it takes 1 seconds to process one message to be on safer side.

		StepVerifier.create(executeWebSocketConnectMono)
			.then(() -> postMessages(messageCount))
			.expectNextCount(0)
			.expectError(TimeoutException.class)
			.verify();

		assertThat(messageCounter.get()).isEqualTo(messageCount);
		*/
	}
}
