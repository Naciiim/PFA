package com.example.homepageBackend.util;

import com.example.homepageBackend.model.dto.*;
import com.example.homepageBackend.service.*;
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
        int page = postingRequest.getPage();
        int size = postingRequest.getSize();


        PageRequest pageRequest = PageRequest.of(page, size);


        Page<PostingDTO> pagedPostingsWithDiffEtat = postingServiceImpl.getPostingsWithDifferentEtat(pageRequest);


        postingWithDiffEtat.clear();


        if (pagedPostingsWithDiffEtat != null && pagedPostingsWithDiffEtat.hasContent()) {
            postingWithDiffEtat.addAll(pagedPostingsWithDiffEtat.getContent());
        }


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
        int page = mouvementRequest.getPage();
        int size = mouvementRequest.getSize();

        PageRequest pageRequest = PageRequest.of(page, size);

        Page<MouvementDTO> pagedMouvementsWithDiffEtat = mouvementServiceImpl.getMouvementsWithDifferentEtat(pageRequest);

        mouvementWithDiffEtat.clear();

        if (pagedMouvementsWithDiffEtat != null && pagedMouvementsWithDiffEtat.hasContent()) {
            mouvementWithDiffEtat.addAll(pagedMouvementsWithDiffEtat.getContent());
        }
        int totalPages = pagedMouvementsWithDiffEtat != null ? pagedMouvementsWithDiffEtat.getTotalPages() : 0;
        boolean hasNextPage = pagedMouvementsWithDiffEtat != null && pagedMouvementsWithDiffEtat.hasNext();
        System.out.println("Paginated mouvements with different etat: " + mouvementWithDiffEtat);
        System.out.println("Total pages: " + totalPages);
        System.out.println("Has more pages: " + hasNextPage);
    }
    public static void paginateAndCachePostingCres(PostingCreRequestDTO PostingCreRequest,
                                                   HomePageServiceImpl homepageServiceImpl,
                                                   List<PostingCreDTO> cachedPostingCres,
                                                   List<PostingCreDTO> PostingCreWithDiffEtat) {
        int page = 0;
        boolean hasMorePages = true;
        do {
            PostingCreRequest.setPage(page);
            System.out.println("Requesting page: " + page);
            Map<String, Object> pagedResponse = homepageServiceImpl.findPostingCres(PostingCreRequest);
            if (pagedResponse == null) {
                break;
            }
            List<PostingCreDTO> pagedPostingCres = (List<PostingCreDTO>) pagedResponse.get("postingCreSearched");
            if (pagedPostingCres != null && !pagedPostingCres.isEmpty()) {
                cachedPostingCres.addAll(pagedPostingCres);
                PostingCreWithDiffEtat.addAll(pagedPostingCres.stream()
                        .filter(PostingCre -> !PostingCre.getEtat().equals("O"))
                        .collect(Collectors.toList()));
            }
            Integer totalPages = (Integer) pagedResponse.get("totalPages");
            hasMorePages = totalPages != null && page < totalPages - 1;
            page++;
            System.out.println("Total pages: " + totalPages + ", Has more pages: " + hasMorePages);
            System.out.println("Cached PostingCres after page " + page + ": " + cachedPostingCres);
            System.out.println("PostingCres with different etat after page " + page + ": " + PostingCreWithDiffEtat);
        } while (hasMorePages);
    }
    public static void paginateAndCachePostingCresWithDiffEtat(PostingCreRequestDTO PostingCreRequest,
                                                               PostingCreServiceImpl PostingCreServiceImpl,
                                                               List<PostingCreDTO> PostingCreWithDiffEtat) {
        int page = PostingCreRequest.getPage();
        int size = PostingCreRequest.getSize();

        PageRequest pageRequest = PageRequest.of(page, size);

        Page<PostingCreDTO> pagedPostingCresWithDiffEtat = PostingCreServiceImpl.getPostingCresWithDifferentEtat(pageRequest);

        PostingCreWithDiffEtat.clear();

        if (pagedPostingCresWithDiffEtat != null && pagedPostingCresWithDiffEtat.hasContent()) {
            PostingCreWithDiffEtat.addAll(pagedPostingCresWithDiffEtat.getContent());
        }

        System.out.println("Paginated PostingCres with different etat: " + PostingCreWithDiffEtat);
        System.out.println("Total pages: " + (pagedPostingCresWithDiffEtat != null ? pagedPostingCresWithDiffEtat.getTotalPages() : 0));
        System.out.println("Has more pages: " + (pagedPostingCresWithDiffEtat != null ? pagedPostingCresWithDiffEtat.hasNext() : false));
    }
}



