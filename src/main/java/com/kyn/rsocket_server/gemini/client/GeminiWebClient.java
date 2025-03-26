package com.kyn.rsocket_server.gemini.client;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;

import com.kyn.rsocket_server.gemini.dto.GeminiRequestDto;
import com.kyn.rsocket_server.gemini.dto.GeminiResponseDto;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class GeminiWebClient {

        private final WebClient webClient;
        private final String apiKey;
        private final String modelName;

        public GeminiWebClient(
                        @Value("${gemini.api.base-url}") String baseUrl,
                        @Value("${gemini.api.key}") String apiKey,
                        @Value("${gemini.api.model}") String modelName) {
                final int size = 16 * 1024 * 1024;
                final ExchangeStrategies strategies = ExchangeStrategies.builder()
                                .codecs(codecs -> codecs.defaultCodecs().maxInMemorySize(size))
                                .build();
                this.webClient = WebClient.builder()
                                .baseUrl(baseUrl)
                                .exchangeStrategies(strategies)
                                .build();
                ;
                this.apiKey = apiKey;
                log.info("apiKey: {}", apiKey);
                this.modelName = modelName;
        }

        public Mono<GeminiResponseDto> generateContent(GeminiRequestDto request) {
                return webClient.post()
                                .uri("/models/{model}:generateContent?key={apiKey}", modelName, apiKey)
                                .bodyValue(request)
                                .retrieve()
                                .bodyToMono(GeminiResponseDto.class)
                                .doOnSuccess(response -> log.info("Generated content successfully"))
                                .doOnError(error -> log.error("Error generating content", error));
        }

        public Flux<GeminiResponseDto> streamGenerateContent(GeminiRequestDto request) {
                return webClient.post()
                                .uri("/models/{model}:streamGenerateContent?key={apiKey}", modelName, apiKey)
                                .bodyValue(request)
                                .retrieve()
                                .bodyToFlux(GeminiResponseDto.class)
                                .doOnNext(response -> log.info("Generated content successfully " + response))
                                .doOnError(error -> log.error("Error streaming content", error));
        }

}
