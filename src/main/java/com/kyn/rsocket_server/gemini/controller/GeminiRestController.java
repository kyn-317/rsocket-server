package com.kyn.rsocket_server.gemini.controller;

import org.springframework.web.bind.annotation.RestController;

import com.kyn.rsocket_server.gemini.dto.GeminiResponseDto;
import com.kyn.rsocket_server.gemini.service.GeminiService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@RestController
@RequiredArgsConstructor
public class GeminiRestController {

    private final GeminiService geminiService;

    @GetMapping("gemini/generate")
    public Mono<GeminiResponseDto> generateTextContent(@RequestParam String param) {
        log.info("Received generate content request: {}", param);
        return geminiService.generateTextContent(param);
    }

    @GetMapping(value = "gemini/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<GeminiResponseDto> streamTextContent(
            @RequestParam String param) {
        log.info("Received generate content request: {}", param);
        return geminiService.streamTextContent(param);
    }

}
