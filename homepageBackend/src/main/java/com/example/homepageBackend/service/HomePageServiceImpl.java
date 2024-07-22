package com.example.homepageBackend.service;

import com.example.homepageBackend.model.dto.PostingDTO;
import com.example.homepageBackend.model.dto.PostingRequestDTO;
import com.example.homepageBackend.util.FileHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service
@Profile(value = {"DEV", "PROD", "REC"})
public class HomePageServiceImpl implements HomePageService {

    private static final Logger logger = LoggerFactory.getLogger(HomePageServiceImpl.class);

    @Autowired
    private PostingServiceImpl postingService;

    @Autowired
    private FileHandler fileHandler; // Injection de FileHandler

    @Override
    public Map<String, Object> validateAndRetrieveData(PostingRequestDTO postingRequest) {
        Map<String, Object> response = new LinkedHashMap<>();
        Pageable pageable = PageRequest.of(postingRequest.getPage(), postingRequest.getSize());

        // Récupérer tous les postings ayant un état différent de 'T'
        Page<PostingDTO> postingsWithDifferentEtat = postingService.getPostingsWithDifferentEtat(pageable);
        List<PostingDTO> postingsListWithDifferentEtat = postingsWithDifferentEtat.getContent();
        response.put("postingWithDiffEtat", postingsListWithDifferentEtat);
        response.put("totalPagesWithDiffEtat", postingsWithDifferentEtat.getTotalPages());
        response.put("totalElementsWithDiffEtat", postingsWithDifferentEtat.getTotalElements());

        // Validation de transactionId et masterReference
        if (fileHandler.validateTransactionIdAndMasterReference(postingRequest.getTransactionid(), postingRequest.getMasterreference())) {
            logger.warn("Validation échouée pour transactionId: {} et masterreference: {}",
                    postingRequest.getTransactionid(), postingRequest.getMasterreference());
            response.put("message", "Transaction ID et Master Reference ne peuvent pas être tous les deux vides.");
            return response;
        }

        // Récupération des postings selon les critères
        Page<PostingDTO> postingsPage;
        if (postingRequest.getTransactionid() != null && !postingRequest.getTransactionid().isEmpty() &&
                (postingRequest.getMasterreference() == null || postingRequest.getMasterreference().isEmpty()) &&
                (postingRequest.getEventreference() == null || postingRequest.getEventreference().isEmpty())) {
            logger.info("Récupération des postings par transactionId: {}", postingRequest.getTransactionid());
            postingsPage = postingService.getPostingsByTransactionId(postingRequest.getTransactionid(), pageable);
        } else if (postingRequest.getMasterreference() != null && !postingRequest.getMasterreference().isEmpty() &&
                (postingRequest.getTransactionid() == null || postingRequest.getTransactionid().isEmpty()) &&
                (postingRequest.getEventreference() == null || postingRequest.getEventreference().isEmpty())) {
            logger.info("Récupération des postings par masterreference: {}", postingRequest.getMasterreference());
            postingsPage = postingService.getPostingsByMasterreference(postingRequest.getMasterreference(), pageable);
        } else if (postingRequest.getTransactionid() != null && !postingRequest.getTransactionid().isEmpty() &&
                postingRequest.getMasterreference() != null && !postingRequest.getMasterreference().isEmpty() &&
                (postingRequest.getEventreference() == null || postingRequest.getEventreference().isEmpty())) {
            logger.info("Récupération des postings par transactionId: {} et masterreference: {}",
                    postingRequest.getTransactionid(), postingRequest.getMasterreference());
            postingsPage = postingService.getPostingsByTransactionIdAndMasterReference(postingRequest.getTransactionid(), postingRequest.getMasterreference(), pageable);
        } else if (postingRequest.getTransactionid() != null && !postingRequest.getTransactionid().isEmpty() &&
                postingRequest.getEventreference() != null && !postingRequest.getEventreference().isEmpty() &&
                (postingRequest.getMasterreference() == null || postingRequest.getMasterreference().isEmpty())) {
            logger.info("Récupération des postings par transactionId: {} et eventreference: {}",
                    postingRequest.getTransactionid(), postingRequest.getEventreference());
            postingsPage = postingService.getPostingsByTransactionidAndEventreference(postingRequest.getTransactionid(), postingRequest.getEventreference(), pageable);
        } else if (postingRequest.getMasterreference() != null && !postingRequest.getMasterreference().isEmpty() &&
                postingRequest.getEventreference() != null && !postingRequest.getEventreference().isEmpty() &&
                (postingRequest.getTransactionid() == null || postingRequest.getTransactionid().isEmpty())) {
            logger.info("Récupération des postings par masterreference: {} et eventreference: {}",
                    postingRequest.getMasterreference(), postingRequest.getEventreference());
            postingsPage = postingService.getPostingsByMasterreferenceAndEventreference(postingRequest.getMasterreference(), postingRequest.getEventreference(), pageable);
        } else if (postingRequest.getTransactionid() != null && !postingRequest.getTransactionid().isEmpty() &&
                postingRequest.getMasterreference() != null && !postingRequest.getMasterreference().isEmpty() &&
                postingRequest.getEventreference() != null && !postingRequest.getEventreference().isEmpty()) {
            logger.info("Récupération des postings par transactionId: {}, masterreference: {} et eventreference: {}",
                    postingRequest.getTransactionid(), postingRequest.getMasterreference(), postingRequest.getEventreference());
            postingsPage = postingService.getPostingsByTransactionidAndMasterreferenceAndEventreference(postingRequest.getTransactionid(), postingRequest.getMasterreference(), postingRequest.getEventreference(), pageable);
        } else {
            logger.warn("Aucun critère de recherche valide fourni.");
            response.put("message", "Veuillez fournir au moins un critère de recherche valide (transactionid ou masterreference).");
            return response;
        }

        response.put("POSTINGSEARCHED", postingsPage.getContent());
        response.put("totalPages", postingsPage.getTotalPages());
        response.put("totalElements", postingsPage.getTotalElements());

        List<PostingDTO> filteredPostings = postingsWithDifferentEtat.getContent().stream()
                .filter(posting -> !fileHandler.isEtatT(posting.getEtat()))
                .collect(Collectors.toList());

        if (!filteredPostings.isEmpty()) {
            StringBuilder message = new StringBuilder();
            message.append("Des postings ont un état différent de 'T' pour les critères fournis.");
            response.put("message", message.toString());
            logger.warn(message.toString());
        }

        return response;
    }

}
