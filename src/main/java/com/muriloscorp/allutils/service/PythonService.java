package com.muriloscorp.allutils.service;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class PythonService {

    private final WebClient webClient;

    public PythonService(WebClient.Builder builder) {
        this.webClient = builder
                .baseUrl("http://localhost:8080")
                .build();
    }

    public byte[] gerarQr(String texto) {
        return webClient.post()
                .uri("/qr")
                .bodyValue(texto)
                .retrieve()
                .bodyToMono(byte[].class)
                .block();
    }
}
