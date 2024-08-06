package com.example.homepageBackend.util;

import com.example.homepageBackend.model.dto.MouvementDTO;
import com.example.homepageBackend.model.dto.MouvementRequestDTO;
import com.example.homepageBackend.model.dto.PostingDTO;
import com.example.homepageBackend.model.dto.PostingRequestDTO;
import com.example.homepageBackend.service.ExportServiceImpl;
import com.example.homepageBackend.service.HomePageServiceImpl;
import com.example.homepageBackend.service.MouvementServiceImpl;
import com.example.homepageBackend.service.PostingServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class Utils {

    @Autowired
    private ExportServiceImpl exportServiceImpl;

    public static Method findGetterMethod(Object object, String fieldName) {
        String getterMethodName = "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
        Method[] methods = object.getClass().getMethods();
        for (Method method : methods) {
            if (method.getName().equals(getterMethodName)) {
                return method;
            }
        }
        return null;
    }

    // Convertit le nom de colonne en nom de champ Java
    public static String convertColumnNameToFieldName(String columnName) {
        StringBuilder fieldNameBuilder = new StringBuilder();
        boolean nextUpperCase = false;
        for (int i = 0; i < columnName.length(); i++) {
            char c = columnName.charAt(i);
            if (c == '_') {
                nextUpperCase = true;
            } else {
                if (nextUpperCase) {
                    fieldNameBuilder.append(Character.toUpperCase(c));
                    nextUpperCase = false;
                } else {
                    fieldNameBuilder.append(Character.toLowerCase(c));
                }
            }
        }
        return fieldNameBuilder.toString();
    }
    public static void paginateAndCachePostings(PostingRequestDTO postingRequest,
                                                 HomePageServiceImpl homepageServiceImpl,
                                                 List<PostingDTO> cachedPostings,
                                                 List<PostingDTO> postingWithDiffEtat) {
        int page = 0;
        boolean hasMorePages = true;
        do {
            postingRequest.setPage(page);
            System.out.println("Requesting page: " + page);
            Map<String, Object> pagedResponse = homepageServiceImpl.findPostings(postingRequest);
            if (pagedResponse == null) {
                break;
            }
            List<PostingDTO> pagedPostings = (List<PostingDTO>) pagedResponse.get("POSTINGSEARCHED");
            if (pagedPostings != null && !pagedPostings.isEmpty()) {
                cachedPostings.addAll(pagedPostings);
                postingWithDiffEtat.addAll(pagedPostings.stream()
                        .filter(posting -> !posting.getEtat().equals("T"))
                        .collect(Collectors.toList()));
            }
            Integer totalPages = (Integer) pagedResponse.get("totalPages");
            hasMorePages = totalPages != null && page < totalPages - 1;
            page++;
            System.out.println("Total pages: " + totalPages + ", Has more pages: " + hasMorePages);
            System.out.println("Cached postings after page " + page + ": " + cachedPostings);
            System.out.println("Postings with different etat after page " + page + ": " + postingWithDiffEtat);
        } while (hasMorePages);
    }
    public static void paginateAndCachePostingsWithDiffEtat(PostingRequestDTO postingRequest,
                                                            PostingServiceImpl postingServiceImpl,
                                                            List<PostingDTO> postingWithDiffEtat) {
        int page = postingRequest.getPage(); // Page requested
        int size = postingRequest.getSize(); // Page size

        // Create a PageRequest with the current page and size
        PageRequest pageRequest = PageRequest.of(page, size);

        // Fetch paginated postings with different states
        Page<PostingDTO> pagedPostingsWithDiffEtat = postingServiceImpl.getPostingsWithDifferentEtat(pageRequest);

        // Clear the existing postings
        postingWithDiffEtat.clear();

        // Add the postings of the current page to the list
        if (pagedPostingsWithDiffEtat != null && pagedPostingsWithDiffEtat.hasContent()) {
            postingWithDiffEtat.addAll(pagedPostingsWithDiffEtat.getContent());
        }

        // Log the results for debugging
        System.out.println("Paginated postings with different etat: " + postingWithDiffEtat);
        System.out.println("Total pages: " + (pagedPostingsWithDiffEtat != null ? pagedPostingsWithDiffEtat.getTotalPages() : 0));
        System.out.println("Has more pages: " + (pagedPostingsWithDiffEtat != null ? pagedPostingsWithDiffEtat.hasNext() : false));
    }

    public static void paginateAndCacheMouvements(MouvementRequestDTO mouvementRequest,
                                                  HomePageServiceImpl homepageServiceImpl,
                                                  List<MouvementDTO> cachedMouvements,
                                                  List<MouvementDTO> mouvementWithDiffEtat) {
        int page = 0;
        boolean hasMorePages = true;
        do {
            mouvementRequest.setPage(page);
            System.out.println("Requesting page: " + page);
            Map<String, Object> pagedResponse = homepageServiceImpl.findMouvements(mouvementRequest);
            if (pagedResponse == null) {
                break;
            }
            List<MouvementDTO> pagedMouvements = (List<MouvementDTO>) pagedResponse.get("mvtSearched");
            if (pagedMouvements != null && !pagedMouvements.isEmpty()) {
                cachedMouvements.addAll(pagedMouvements);
                mouvementWithDiffEtat.addAll(pagedMouvements.stream()
                        .filter(mouvement -> !mouvement.getEtat().equals("T"))
                        .collect(Collectors.toList()));
            }
            Integer totalPages = (Integer) pagedResponse.get("totalPages");
            hasMorePages = totalPages != null && page < totalPages - 1;
            page++;
            System.out.println("Total pages: " + totalPages + ", Has more pages: " + hasMorePages);
            System.out.println("Cached mouvements after page " + page + ": " + cachedMouvements);
            System.out.println("Mouvements with different etat after page " + page + ": " + mouvementWithDiffEtat);
        } while (hasMorePages);
    }

    public static void paginateAndCacheMouvementsWithDiffEtat(MouvementRequestDTO mouvementRequest,
                                                              MouvementServiceImpl mouvementServiceImpl,
                                                              List<MouvementDTO> mouvementWithDiffEtat) {
        int page = mouvementRequest.getPage(); // Page requested
        int size = mouvementRequest.getSize(); // Page size

        // Create a PageRequest with the current page and size
        PageRequest pageRequest = PageRequest.of(page, size);

        // Fetch paginated mouvements with different states
        Page<MouvementDTO> pagedMouvementsWithDiffEtat = mouvementServiceImpl.getMouvementsWithDifferentEtat(pageRequest);

        // Clear the existing mouvements
        mouvementWithDiffEtat.clear();

        // Add the mouvements of the current page to the list
        if (pagedMouvementsWithDiffEtat != null && pagedMouvementsWithDiffEtat.hasContent()) {
            mouvementWithDiffEtat.addAll(pagedMouvementsWithDiffEtat.getContent());
        }
        int totalPages = pagedMouvementsWithDiffEtat != null ? pagedMouvementsWithDiffEtat.getTotalPages() : 0;
        boolean hasNextPage = pagedMouvementsWithDiffEtat != null && pagedMouvementsWithDiffEtat.hasNext();
        // Log the results for debugging
        System.out.println("Paginated mouvements with different etat: " + mouvementWithDiffEtat);
        System.out.println("Total pages: " + totalPages);
        System.out.println("Has more pages: " + hasNextPage);
    }

}



