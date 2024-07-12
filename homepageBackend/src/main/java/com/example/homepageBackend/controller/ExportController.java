package com.example.homepageBackend.controller;

import com.example.homepageBackend.service.interfaces.ExportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/export")
public class ExportController {


    private final ExportService exportService;
@Autowired
    public ExportController(ExportService exportService) {
        this.exportService = exportService;
    }

//    @GetMapping("/postings/{transactionId}")
//    public ResponseEntity<ByteArrayResource> exportPostings(@PathVariable String transactionId) throws IOException {
//        byte[] data = exportService.exportPostings(transactionId);
//        return createResponse(data, "postings.xlsx");
//    }
//
//    private ResponseEntity<ByteArrayResource> createResponse(byte[] data, String filename) {
//        ByteArrayResource resource = new ByteArrayResource(data);
//
//        return ResponseEntity.ok()
//                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + filename)
//                .contentType(MediaType.APPLICATION_OCTET_STREAM)
//                .contentLength(data.length)
//                .body(resource);
//    }
}
