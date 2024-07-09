package com.example.homepageBackend.controller;

import com.example.homepageBackend.service.ExportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/export")
public class ExportController {


    private final ExportService exportService;
@Autowired
    public ExportController(ExportService exportService) {
        this.exportService = exportService;
    }

    @GetMapping("/postings/{transactionId}")
    public ResponseEntity<ByteArrayResource> exportPostings(@PathVariable String transactionId) throws IOException {
        byte[] data = exportService.exportPostings(transactionId);
        return createResponse(data, "postings.xlsx");
    }

    @GetMapping("/mouvements/{transactionId}")
    public ResponseEntity<ByteArrayResource> exportMouvements(@PathVariable String transactionId) throws IOException {
        byte[] data = exportService.exportMouvements(transactionId);
        return createResponse(data, "mouvements.xlsx");
    }

    @GetMapping("/cres/{transactionId}")
    public ResponseEntity<ByteArrayResource> exportCres(@PathVariable String transactionId) throws IOException {
        byte[] data = exportService.exportCres(transactionId);
        return createResponse(data, "cres.xlsx");
    }

    @GetMapping("/all/{transactionId}")
    public ResponseEntity<ByteArrayResource> exportAll(@PathVariable String transactionId) throws IOException {
        byte[] data = exportService.exportAll(transactionId);
        return createResponse(data, "export_all.xlsx");
    }

    private ResponseEntity<ByteArrayResource> createResponse(byte[] data, String filename) {
        ByteArrayResource resource = new ByteArrayResource(data);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + filename)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .contentLength(data.length)
                .body(resource);
    }
}
