package com.kyn.rsocket_server.gemini.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GeminiRequestDto {
    private List<Content> contents;
    private GenerationConfig generationConfig;
    private String model;
    //    private List<SafetySetting> safetySettings;

    public GeminiRequestDto(String prompt) {
        this.contents = new ArrayList<>();
        Part part = Part.builder().text(prompt).build();
        this.contents.add(Content.builder().role("user").parts(List.of(part)).build());
        this.generationConfig = new GenerationConfig();
        this.model = "gemini-2.0-flash-lite";
        //this.safetySettings = new ArrayList<>();
    }
}