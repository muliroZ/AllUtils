package com.muriloscorp.allutils.controller;

import com.muriloscorp.allutils.service.PythonService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/qr")
public class QrController {

    private final PythonService pythonService;

    public QrController(PythonService pythonService) {
        this.pythonService = pythonService;
    }

    @PostMapping("/")
    public ResponseEntity<byte[]> gerarQr(@RequestBody String texto) {
        byte[] qrCode = pythonService.gerarQr(texto);
        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.IMAGE_PNG)
                .body(qrCode);
    }
}
