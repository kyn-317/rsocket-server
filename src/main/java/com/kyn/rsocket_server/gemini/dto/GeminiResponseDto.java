package com.kyn.rsocket_server.gemini.dto;

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
public class GeminiResponseDto {
    private List<Candidate> candidates;
    private PromptFeedback promptFeedback;
}