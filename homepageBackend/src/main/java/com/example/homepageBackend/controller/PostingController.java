package com.example.homepageBackend.controller;

import com.example.homepageBackend.model.dto.PostingDTO;
import com.example.homepageBackend.model.dto.PostingRequestDTO;
import com.example.homepageBackend.service.ExportServiceImpl;
import com.example.homepageBackend.service.HomePageServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:4200")
public class PostingController {

    @Autowired
    private HomePageServiceImpl homepageServiceImpl;

    @Autowired
    private ExportServiceImpl exportServiceImpl;

    // Variable pour stocker les postings récupérés
    private final List<PostingDTO> cachedPostings= new ArrayList<>();;

    @PostMapping("/getPosting")
    public ResponseEntity<Map<String, Object>> getPostings(@RequestBody PostingRequestDTO postingRequest) {
        System.out.println("Received DTO: " + postingRequest);
        Map<String, Object> response = homepageServiceImpl.validateAndRetrieveData(postingRequest);

        // Accumuler tous les postings récupérés
        List<PostingDTO> postings = (List<PostingDTO>) response.get("POSTINGSEARCHED");
        System.out.println("Postings: " + postings);
        cachedPostings.addAll(postings); // Ajouter les postings récupérés à la liste en cache

        System.out.println("Cached Postings: " + cachedPostings);
        return ResponseEntity.ok(response);
    }


    @GetMapping("/exportPostings")
    public void exportPostings(HttpServletResponse response) {
        try {
            // Vérifier si les postings sont en cache
            if (cachedPostings == null || cachedPostings.isEmpty()) {
                response.setStatus(HttpStatus.NO_CONTENT.value());
                response.getWriter().write("Aucun posting disponible pour l'exportation");
                return;
            }

            // Configurez la réponse pour l'exportation Excel
            response.setContentType("application/octet-stream");
            response.setHeader("Content-Disposition", "attachment; filename=postings.xlsx");

            // Créer un flux de sortie pour écrire le fichier Excel
            try (OutputStream outputStream = response.getOutputStream()) {
                exportServiceImpl.exportToExcel(cachedPostings, outputStream);
                outputStream.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
    }

}
