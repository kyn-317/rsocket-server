package com.kyn.rsocket_server.gemini;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.test.context.TestPropertySource;
import org.springframework.util.MimeTypeUtils;

import com.kyn.rsocket_server.gemini.dto.GeminiResponseDto;

import io.rsocket.transport.netty.client.TcpClientTransport;
import io.rsocket.transport.netty.client.WebsocketClientTransport;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class RSocketTest {

    private RSocketRequester requester;

    @Autowired
    private RSocketRequester.Builder builder;

    @BeforeAll
    public void setup() {
        this.requester = this.builder
                .transport(WebsocketClientTransport.create("localhost", 8081));
    }

    @Test
    public void gemini1() {
        Mono<GeminiResponseDto> mono = this.requester.route("gemini.generate")
                .data("hello gemini")
                .retrieveMono(GeminiResponseDto.class);

        StepVerifier.create(mono)
                .expectNextCount(1)
                .verifyComplete();
    }

    @Test
    public void gemini2() {
        Mono<String> mono = this.requester.route("gemini.generate2")
                .data("prompt")
                .retrieveMono(String.class);

        StepVerifier.create(mono)
                .expectNext("텍스트 생성 응답: prompt")
                .verifyComplete();
    }

    @Test
    public void gemini3() {
        Mono<String> mono = this.requester.route("gemini.test")
                .data("payloadpromptpayloadprompt")
                .retrieveMono(String.class);

        StepVerifier.create(mono)
                .expectNextCount(1)
                .verifyComplete();
    }
}
