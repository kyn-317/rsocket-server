package com.kyn.rsocket_server.gemini.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.kyn.rsocket_server.gemini.client.GeminiWebClient;
import com.kyn.rsocket_server.gemini.dto.Content;
import com.kyn.rsocket_server.gemini.dto.GeminiRequestDto;
import com.kyn.rsocket_server.gemini.dto.GeminiResponseDto;
import com.kyn.rsocket_server.gemini.dto.GenerationConfig;
import com.kyn.rsocket_server.gemini.dto.Part;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class GeminiService {

    private final GeminiWebClient geminiWebClient;

    public Mono<GeminiResponseDto> generateTextContent(String prompt) {
        return geminiWebClient.generateContent(new GeminiRequestDto(prompt)).doOnNext(System.out::println);
    }

    public Flux<GeminiResponseDto> streamTextContent(String prompt) {
        return geminiWebClient.streamGenerateContent(new GeminiRequestDto(prompt));
    }

}