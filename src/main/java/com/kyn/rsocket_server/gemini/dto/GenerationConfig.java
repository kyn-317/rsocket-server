package com.kyn.rsocket_server.gemini.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@AllArgsConstructor
@Builder
public class GenerationConfig {

    private float temperature;
    private float topP;
    private float topK;
    private int maxOutputTokens;
    private String responseMimeType;

    public GenerationConfig() {

        this.temperature = 1.0f;
        this.topP = 0.95f;
        this.topK = 40;
        this.maxOutputTokens = 8192;
        this.responseMimeType = "text/plain";
    }
}
