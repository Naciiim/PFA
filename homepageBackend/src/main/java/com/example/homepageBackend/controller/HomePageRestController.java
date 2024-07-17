package com.example.homepageBackend.controller;

import com.example.homepageBackend.model.dto.PostingDTO;
import com.example.homepageBackend.service.implementation.ExportServiceImpl;
import com.example.homepageBackend.service.implementation.HomePageServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
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
        cachedPostings = (List<PostingDTO>) response.get("POSTINGSEARCHED");
        System.out.println("Cached Postings: " + cachedPostings);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/exportPostings")
    public ResponseEntity<String> exportPostings(HttpServletResponse response) {
        try {
            // Vérifier si les postings sont en cache
            if (cachedPostings == null || cachedPostings.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Aucun posting disponible pour l'exportation");
            }

            // Afficher le contenu de cachedPostings pour le débogage
            System.out.println("Cached Postings: " + cachedPostings);

            // Configurez la réponse pour l'exportation Excel
            response.setContentType("application/octet-stream");
            response.setHeader("Content-Disposition", "attachment; filename=postings.xlsx");

            // Exporter les postings en cache vers Excel en utilisant ExportServiceImpl
            exportServiceImpl.exportToExcel(cachedPostings, response.getOutputStream());

            return ResponseEntity.ok("Exportation des postings vers Excel réussie");
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur lors de l'exportation vers Excel : " + e.getMessage());
        }
    }

}
