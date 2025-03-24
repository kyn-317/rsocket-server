package com.kyn.rsocket_server.gemini.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Controller;

import com.kyn.rsocket_server.gemini.dto.GeminiResponseDto;
import com.kyn.rsocket_server.gemini.service.GeminiService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
@Controller
@RequiredArgsConstructor
public class GeminiController {

    private final GeminiService geminiService;

    @MessageMapping("gemini.generate")
    public Mono<GeminiResponseDto> generateContent(@Payload String prompt) {
        log.info("Received generate content request: {}", prompt);
        return geminiService.generateTextContent(prompt);
    }

    @MessageMapping("gemini.generate2")
    public Mono<String> generateContentAsString(@Payload String prompt) {
        log.info("Received generate content as string request: {}", prompt);
        return Mono.just("텍스트 생성 응답: " + prompt);
    }

    @MessageMapping("gemini.stream")
    public Mono<GeminiResponseDto> streamContent(@Payload String prompt) {
        log.info("Received stream content request: {}", prompt);
        return geminiService.streamTextContent(prompt);
    }

    @MessageMapping("gemini.test")
    public Mono<String> test(@Payload String prompt) {
        log.info("Received test request: {}", prompt);
        return Mono.just("테스트 성공! 입력: " + prompt);
    }
}