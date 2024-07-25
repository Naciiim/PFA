package com.example.homepageBackend.util;

import com.example.homepageBackend.model.dto.PostingDTO;
import com.example.homepageBackend.model.dto.PostingRequestDTO;
import com.example.homepageBackend.service.HomePageServiceImpl;
import com.example.homepageBackend.service.PostingServiceImpl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class Utils {

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
            Map<String, Object> pagedResponse = homepageServiceImpl.validateAndRetrieveData(postingRequest);
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





}



