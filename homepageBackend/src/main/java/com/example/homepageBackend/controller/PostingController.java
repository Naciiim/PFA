package com.example.homepageBackend.controller;

import com.example.homepageBackend.model.dto.PostingDTO;
import com.example.homepageBackend.model.dto.PostingRequestDTO;
import com.example.homepageBackend.service.ExportServiceImpl;
import com.example.homepageBackend.service.HomePageServiceImpl;
import com.example.homepageBackend.service.PostingServiceImpl;
import com.example.homepageBackend.util.FileHandler;
import com.example.homepageBackend.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:4200")
public class PostingController {

    @Autowired
    private HomePageServiceImpl homepageServiceImpl;

    @Autowired
    private PostingServiceImpl postingServiceImpl;
    @Autowired
    private FileHandler fileHandler;
    // Variables pour stocker les postings en cache
    private final List<PostingDTO> cachedPostings = new ArrayList<>();
    private final List<PostingDTO> postingWithDiffEtat = new ArrayList<>();
    @Autowired
    private ExportServiceImpl  exportServiceImpl;

    @PostMapping("/getPosting")
    public ResponseEntity<Map<String, Object>> getPostings(@RequestBody PostingRequestDTO postingRequest) {
        System.out.println("Received DTO: " + postingRequest);

        // Get response from the service
        Map<String, Object> response = homepageServiceImpl.validateAndRetrieveData(postingRequest);

        if (response == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }

        // Get postings from the response, default to empty list if null
        List<PostingDTO> postings = (List<PostingDTO>) response.get("POSTINGSEARCHED");
        if (postings == null) {
            postings = new ArrayList<>();
        }

        // Handle caching logic based on page number
        if (postingRequest.getPage() == 0) {
            System.out.println("First page request, clearing cache...");

            // Clear the cache for the first page request
            cachedPostings.clear();
            postingWithDiffEtat.clear();

            // Add postings with different states to the list
            List<PostingDTO> postingsWithDiffEtat = (List<PostingDTO>) response.get("postingWithDiffEtat");
            if (postingsWithDiffEtat != null) {
                postingWithDiffEtat.addAll(postingsWithDiffEtat);
            }

            // Paginate postings and cache them
            Utils.paginateAndCachePostings(postingRequest, homepageServiceImpl, cachedPostings, postingWithDiffEtat);
            Utils.paginateAndCachePostingsWithDiffEtat(postingRequest, postingServiceImpl, postingWithDiffEtat);
        } else {
            // Paginate postings with different states based on the current page
            Utils.paginateAndCachePostingsWithDiffEtat(postingRequest, postingServiceImpl, postingWithDiffEtat);
        }

        System.out.println("Paginated postings with diff etat: " + postingWithDiffEtat);

        response.put("postingWithDiffEtat", postingWithDiffEtat);
        return ResponseEntity.ok(response);
    }


    @GetMapping("/exportPostings")
    public void exportPostings(@RequestParam(required = false) String type, HttpServletResponse response) {
        try {
            List<PostingDTO> postingsToExport;

            if ("cached".equals(type)) {
                postingsToExport = cachedPostings;
            } else if ("diffEtat".equals(type)) {
                // Retrieve all pages of postings with different states
                postingsToExport = postingServiceImpl.getAllPostingsWithDifferentEtat();
            } else {
                postingsToExport = new ArrayList<>();
                postingsToExport.addAll(cachedPostings);
                postingsToExport.addAll(postingServiceImpl.getAllPostingsWithDifferentEtat());
            }

            // Check if postings are available
            if (postingsToExport.isEmpty()) {
                response.setStatus(HttpStatus.NO_CONTENT.value());
                response.getWriter().write("Aucun posting disponible pour l'exportation");
                return;
            }

            // Configure the response for exporting as Excel
            response.setContentType("application/octet-stream");
            response.setHeader("Content-Disposition", "attachment; filename=postings.xlsx");

            // Create an output stream for the Excel file
            try (OutputStream outputStream = response.getOutputStream()) {
                exportServiceImpl.exportToExcel(postingsToExport, outputStream);
                outputStream.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
    }





}

