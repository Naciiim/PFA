package com.example.homepageBackend.controller;

import com.example.homepageBackend.model.dto.PostingDTO;
import com.example.homepageBackend.service.implementation.ExportServiceImpl;
import com.example.homepageBackend.service.implementation.HomePageServiceImpl;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:4200")
public class HomePageRestController {

    @Autowired
    private HomePageServiceImpl homepageServiceImpl;
    @Autowired
    private ExportServiceImpl exportServiceImpl;

    // Variable pour stocker les postings récupérés
    private List<PostingDTO> cachedPostings;

    @PostMapping("/getPosting")
    public ResponseEntity<Map<String, Object>> getPostings(@RequestBody PostingDTO posting) {
        System.out.println("Received DTO: " + posting);
        Map<String, Object> response = homepageServiceImpl.validateAndRetrieveData(posting);

        // Stocker les postings récupérés dans la variable de classe
        cachedPostings = (List<PostingDTO>) response.get("postingsWithTEtat");

        return ResponseEntity.ok(response);
    }

    @GetMapping("/exportPostings")
    public void exportPostings(HttpServletResponse response) {
        try {
            // Configure the response
            response.setContentType("application/octet-stream");
            response.setHeader("Content-Disposition", "attachment; filename=postings.xlsx");

            // Export the cached postings to Excel
            exportServiceImpl.exportToExcel(cachedPostings, response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
