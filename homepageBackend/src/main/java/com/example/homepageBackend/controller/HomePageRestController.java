package com.example.homepageBackend.controller;

import com.example.homepageBackend.service.HomepageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:4200")
public class HomePageRestController {

    @Autowired
    private HomepageService homepageService;
    private static final Logger logger = LoggerFactory.getLogger(HomePageRestController.class);

    @GetMapping("/validate")
    public ResponseEntity<Map<String, Object>> validateAndRetrieveData(@RequestParam(required = false) String transactionId,
                                                                       @RequestParam(required = false) String ref,
                                                                       @RequestParam(required = false) String event) {
        logger.info("Requête reçue avec transactionId={}, ref={}, event={}", transactionId, ref, event);

        if ((transactionId == null || transactionId.isEmpty()) && (ref == null || ref.isEmpty())) {
            Map<String, Object> response = new LinkedHashMap<>();
            response.put("error", "Erreur: Vous devez fournir soit transactionId soit ref.");
            return ResponseEntity.badRequest().body(response);
        }

        Map<String, Object> response = homepageService.validateAndRetrieveData(transactionId, ref, event);
        return ResponseEntity.ok(response);
    }
}
