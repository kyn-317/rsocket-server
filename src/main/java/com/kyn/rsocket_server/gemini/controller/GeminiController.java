package com.kyn.rsocket_server.gemini.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Controller;

import com.kyn.rsocket_server.gemini.dto.GeminiResponseDto;
import com.kyn.rsocket_server.gemini.service.GeminiService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
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

    @MessageMapping("gemini.stream")
    public Flux<GeminiResponseDto> streamContent(@Payload String prompt) {
        log.info("Received stream content request: {}", prompt);
        return geminiService.streamTextContent(prompt);
    }

}