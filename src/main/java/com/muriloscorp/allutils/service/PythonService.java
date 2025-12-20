package com.muriloscorp.allutils.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class PythonService {

    private final WebClient webClient;

    public PythonService(WebClient.Builder builder, @Value("${python.base-url}") String baseUrl) {
        this.webClient = builder
                .baseUrl(baseUrl)
                .build();
    }

    public byte[] gerarQr(String texto) {
        return webClient.post()
                .uri("/qr/")
                .bodyValue(texto)
                .retrieve()
                .bodyToMono(byte[].class)
                .block();
    }

    public byte[] converterPdfParaDocx(byte[] pdfBytes, String nomeArquivo) {
        ByteArrayResource resource = new ByteArrayResource(pdfBytes) {
            @Override
            public String getFilename() {
                return nomeArquivo;
            }
        };

        MultipartBodyBuilder builder = new MultipartBodyBuilder();
        builder.part("file", resource, MediaType.APPLICATION_PDF);

        return webClient.post()
                .uri("/convert/pdf-to-docx")
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .body(BodyInserters.fromMultipartData(builder.build()))
                .retrieve()
                .bodyToMono(byte[].class)
                .block();
    }
}
