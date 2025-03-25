package com.kyn.rsocket_server.gemini;

import java.net.URI;
import java.time.Duration;

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
import reactor.core.publisher.Flux;
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
                                .transport(WebsocketClientTransport
                                                .create(URI.create("ws://localhost:8081/rsocket")));
        }

        @Test
        public void geminiTest() {
                Mono<GeminiResponseDto> mono = this.requester.route("gemini.generate")
                                .data("hello gemini")
                                .retrieveMono(GeminiResponseDto.class);

                StepVerifier.create(mono)
                                .expectNextCount(1)
                                .verifyComplete();
        }

        @Test
        public void geminiStream() {
                System.out.println("geminiStream 테스트 시작...");

                Flux<GeminiResponseDto> flux = this.requester.route("gemini.stream")
                                .data("100자분량의 짧은 시")
                                .retrieveFlux(GeminiResponseDto.class)
                                .doOnNext(response -> System.out.println("스트림 응답 수신: " + response))
                                .doOnError(error -> System.err.println("스트림 오류 발생: " + error.getMessage()));

                StepVerifier.create(flux)
                                .expectNextCount(1)
                                .thenCancel() // 스트림이므로 계속 데이터가 올 수 있어 취소
                                .verify(Duration.ofSeconds(10));

                System.out.println("geminiStream 테스트 완료!");
        }
}
