package com.muriloscorp.allutils.controller;

import com.muriloscorp.allutils.service.PythonService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/converter")
public class ConversorController {
    
    private final PythonService pythonService;

    public ConversorController(PythonService pythonService) {
        this.pythonService = pythonService;
    }

    @PostMapping(value = "/pdf-to-docx", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<byte[]> converterPdfParaDocx(@RequestParam("file") MultipartFile file) throws IOException {
        if (!MediaType.APPLICATION_PDF_VALUE.equals(file.getContentType())) {
            return ResponseEntity.badRequest().body("O arquivo enviado não é um PDF válido.".getBytes());
        }

        String originalFilename = file.getOriginalFilename();
        if (originalFilename == null) {
            originalFilename = "arquivo.pdf";
        }

        byte[] docxBytes = pythonService.converterPdfParaDocx(file.getBytes(), originalFilename);

        String filename = originalFilename.replace(".pdf", ".docx");

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"")
                .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.wordprocessingml.document"))
                .body(docxBytes);
    }
}
