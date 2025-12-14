package com.muriloscorp.allutils.controller;

import com.muriloscorp.allutils.service.PythonService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PythonController {

    public PythonService pythonService;

    public PythonController(PythonService pythonService) {
        this.pythonService = pythonService;
    }

    @PostMapping("/qr")
    public ResponseEntity<byte[]> gerarQr(@RequestBody String texto) {
        byte[] qrCode = pythonService.gerarQr(texto);
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_PNG)
                .body(qrCode);
    }
}
