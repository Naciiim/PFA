package com.example.homepageBackend.controller;

import com.example.homepageBackend.model.dto.PostingDTO;
import com.example.homepageBackend.model.entity.Posting;
import com.example.homepageBackend.service.HomePageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:4200")
public class HomePageRestController {

    @Autowired
    private HomePageService homepageService;

    @GetMapping("/getPosting")
    public ResponseEntity<Map<String, Object>> getPostings(@RequestBody PostingDTO posting) {
        System.out.println("Received DTO: " + posting);
        Map<String, Object> response = homepageService.validateAndRetrieveData(posting);
        return ResponseEntity.ok(response);
    }
}
