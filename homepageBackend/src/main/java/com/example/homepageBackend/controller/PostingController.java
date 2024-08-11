package com.example.homepageBackend.controller;

import com.example.homepageBackend.model.dto.*;
import com.example.homepageBackend.service.*;
import com.example.homepageBackend.util.Utils;
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
    private PostingServiceImpl postingServiceImpl;
    @Autowired
    private PostingCreServiceImpl postingCreServiceImpl;
    @Autowired
    private MouvementServiceImpl mouvementServiceImpl;

    private final List<PostingDTO> cachedPostings = new ArrayList<>();
    private final List<PostingDTO> postingWithDiffEtat = new ArrayList<>();
    private final List<PostingCreDTO> cachedPostingCres = new ArrayList<>();
    private final List<PostingCreDTO> postingCreWithDiffEtat = new ArrayList<>();
    private final List<MouvementDTO> cachedMouvements = new ArrayList<>();
    private final List<MouvementDTO> mvtWithEtatDiff = new ArrayList<>();
    @Autowired
    private ExportServiceImpl  exportServiceImpl;

    @PostMapping("/getPosting")
    public ResponseEntity<Map<String, Object>> getPostings(@RequestBody PostingRequestDTO postingRequest) {
        System.out.println("Received DTO: " + postingRequest);

        // Get response from the service
        Map<String, Object> response = homepageServiceImpl.findPostings(postingRequest);

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


    @GetMapping("/exportPosting")
    public void exportPostings(HttpServletResponse response) {
        try {
            List<PostingDTO> postingsToExport = new ArrayList<>();

            if (!cachedPostings.isEmpty()) {
                postingsToExport.addAll(cachedPostings);
            }else {
                List<PostingDTO> allPostingsWithDiffEtat = postingServiceImpl.getAllPostingsWithDifferentEtat();
                postingsToExport.addAll(allPostingsWithDiffEtat);
            }

            // Logging for debugging
            System.out.println("Cached postings count: " + cachedPostings.size());
            System.out.println("Postings with different state count: " + postingWithDiffEtat.size());
            System.out.println("Total postings to export: " + postingsToExport.size());

            if (postingsToExport.isEmpty()) {
                response.setStatus(HttpStatus.NO_CONTENT.value());
                response.getWriter().write("Aucun posting disponible pour l'exportation");
                return;
            }

            response.setContentType("application/octet-stream");
            response.setHeader("Content-Disposition", "attachment; filename=postings.xlsx");

            try (OutputStream outputStream = response.getOutputStream()) {
                exportServiceImpl.exportToExcel(postingsToExport, outputStream);
                outputStream.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
    }
    @PostMapping("/getMouvement")
    public ResponseEntity<Map<String, Object>> getMouvements(@RequestBody MouvementRequestDTO mouvementRequest) {
        System.out.println("Received DTO: " + mouvementRequest);

        // Get response from the service
        Map<String, Object> response = homepageServiceImpl.findMouvements(mouvementRequest);

        if (response == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }

        // Get mouvements from the response, default to empty list if null
        List<MouvementDTO> mouvements = (List<MouvementDTO>) response.get("mvtSearched");
        if (mouvements == null) {
            mouvements = new ArrayList<>();
        }

        // Handle caching logic based on page number
        if (mouvementRequest.getPage() == 0) {
            System.out.println("First page request, clearing cache...");

            // Clear the cache for the first page request
            cachedMouvements.clear();
            mvtWithEtatDiff.clear();

            // Add mouvements with different states to the list
            List<MouvementDTO> mouvementsWithDiffEtat = (List<MouvementDTO>) response.get("mvtWithEtatDiff");
            if (mouvementsWithDiffEtat != null) {
                mvtWithEtatDiff.addAll(mouvementsWithDiffEtat);
            }

            // Paginate mouvements and cache them
            Utils.paginateAndCacheMouvements(mouvementRequest, homepageServiceImpl, cachedMouvements, mvtWithEtatDiff);
            Utils.paginateAndCacheMouvementsWithDiffEtat(mouvementRequest, mouvementServiceImpl, mvtWithEtatDiff);
        } else {
            // Paginate mouvements with different states based on the current page
            Utils.paginateAndCacheMouvementsWithDiffEtat(mouvementRequest, mouvementServiceImpl, mvtWithEtatDiff);
        }

        System.out.println("Paginated mouvements with diff etat: " + mvtWithEtatDiff);

        response.put("mvtWithEtatDiff", mvtWithEtatDiff);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/exportMouvement")
    public void exportMouvements(HttpServletResponse response) {
        try {
            List<MouvementDTO> mouvementsToExport = new ArrayList<>();

            if (!cachedMouvements.isEmpty()) {
                mouvementsToExport.addAll(cachedMouvements);
            } else {
                List<MouvementDTO> allMouvementsWithDiffEtat = mouvementServiceImpl.getAllMouvementsWithDifferentEtat();
                mouvementsToExport.addAll(allMouvementsWithDiffEtat);
            }

            // Logging for debugging
            System.out.println("Cached Mouvements count: " + cachedMouvements.size());
            System.out.println("Mouvements with different state count: " + mvtWithEtatDiff.size());
            System.out.println("Total Mouvements to export: " + mouvementsToExport.size());

            if (mouvementsToExport.isEmpty()) {
                response.setStatus(HttpStatus.NO_CONTENT.value());
                response.getWriter().write("Aucun Mouvement disponible pour l'exportation");
                return;
            }

            response.setContentType("application/octet-stream");
            response.setHeader("Content-Disposition", "attachment; filename=Mouvements.xlsx");

            try (OutputStream outputStream = response.getOutputStream()) {
                exportServiceImpl.exportToExcel(mouvementsToExport, outputStream);
                outputStream.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
    }
@PostMapping("/getPostingCre")
    public ResponseEntity<Map<String, Object>> getPostingCre(@RequestBody PostingCreRequestDTO postingCreRequestDTO) {

    System.out.println("Received DTO: " + postingCreRequestDTO);

    // Get response from the service
    Map<String, Object> response = homepageServiceImpl.findPostingCres(postingCreRequestDTO);

    if (response == null) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
    }

    // Get postings from the response, default to empty list if null
    List<PostingCreDTO> postingCres = (List<PostingCreDTO>) response.get("postingCreSearched");
    if (postingCres == null) {
        postingCres = new ArrayList<>();
    }

    // Handle caching logic based on page number
    if (postingCreRequestDTO.getPage() == 0) {
        System.out.println("First page request, clearing cache...");

        // Clear the cache for the first page request
        cachedPostingCres.clear();
        postingCreWithDiffEtat.clear();

        // Add postings with different states to the list
        List<PostingCreDTO> postingCresWithDiffEtat = (List<PostingCreDTO>) response.get("postingCreWithDiffEtat");
        if (postingCresWithDiffEtat != null) {
            postingCreWithDiffEtat.addAll(postingCresWithDiffEtat);
        }

        // Paginate postings and cache them
        Utils.paginateAndCachePostingCres(postingCreRequestDTO, homepageServiceImpl, cachedPostingCres, postingCreWithDiffEtat);
        Utils.paginateAndCachePostingCresWithDiffEtat(postingCreRequestDTO, postingCreServiceImpl, postingCreWithDiffEtat);
    } else {
        // Paginate postings with different states based on the current page
        Utils.paginateAndCachePostingCresWithDiffEtat(postingCreRequestDTO, postingCreServiceImpl, postingCreWithDiffEtat);
    }

    System.out.println("Paginated postings with diff etat: " + postingCreWithDiffEtat);

    response.put("postingCreWithDiffEtat", postingCreWithDiffEtat);
    return ResponseEntity.ok(response);
}
    @GetMapping("/exportPostingCre")
    public void exportPostingCres(HttpServletResponse response) {
        try {
            List<PostingCreDTO> postingCresToExport = new ArrayList<>();

            if (!cachedPostingCres.isEmpty()) {
                postingCresToExport.addAll(cachedPostingCres);
            }else {
                List<PostingCreDTO> allPostingCresWithDiffEtat = postingCreServiceImpl.getAllPostingCreWithDifferentEtat();
                postingCresToExport.addAll(allPostingCresWithDiffEtat);
            }

            // Logging for debugging
            System.out.println("Cached postingCres count: " + cachedPostingCres.size());
            System.out.println("PostingCres with different state count: " + postingCreWithDiffEtat.size());
            System.out.println("Total postingCres to export: " + postingCresToExport.size());

            if (postingCresToExport.isEmpty()) {
                response.setStatus(HttpStatus.NO_CONTENT.value());
                response.getWriter().write("Aucun postingCre disponible pour l'exportation");
                return;
            }

            response.setContentType("application/octet-stream");
            response.setHeader("Content-Disposition", "attachment; filename=postingCres.xlsx");

            try (OutputStream outputStream = response.getOutputStream()) {
                exportServiceImpl.exportToExcel(postingCresToExport, outputStream);
                outputStream.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
    }
}


