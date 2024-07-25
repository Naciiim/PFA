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
    private FileHandler fileHandler;

    @Override
    public Map<String, Object> validateAndRetrieveData(PostingRequestDTO postingRequest) {
        Map<String, Object> response = new LinkedHashMap<>();
        Pageable pageable = PageRequest.of(postingRequest.getPage(), postingRequest.getSize());

        try {
            Page<PostingDTO> postingsWithDifferentEtat = postingService.getPostingsWithDifferentEtat(pageable);
            List<PostingDTO> postingsListWithDifferentEtat = postingsWithDifferentEtat.getContent();
            response.put("postingWithDiffEtat", postingsListWithDifferentEtat);
            response.put("totalPagesWithDiffEtat", postingsWithDifferentEtat.getTotalPages());
            response.put("totalElementsWithDiffEtat", postingsWithDifferentEtat.getTotalElements());

            if (fileHandler.validateTransactionIdAndMasterReference(postingRequest.getTransactionid(), postingRequest.getMasterreference())) {
                logger.warn("Validation échouée pour transactionId: {} et masterreference: {}",
                        postingRequest.getTransactionid(), postingRequest.getMasterreference());
                response.put("message", "Transaction ID et Master Reference ne peuvent pas être tous les deux vides.");
                return response;
            }

            Page<PostingDTO> postingsPage = null;
            StringBuilder logMessage = new StringBuilder("Récupération des postings par ");

            if (postingRequest.getTransactionid() != null && !postingRequest.getTransactionid().isEmpty()) {
                logMessage.append("transactionId: ").append(postingRequest.getTransactionid()).append(" ");
            }
            if (postingRequest.getMasterreference() != null && !postingRequest.getMasterreference().isEmpty()) {
                logMessage.append("masterreference: ").append(postingRequest.getMasterreference()).append(" ");
            }
            if (postingRequest.getEventreference() != null && !postingRequest.getEventreference().isEmpty()) {
                logMessage.append("eventreference: ").append(postingRequest.getEventreference()).append(" ");
            }

            logger.info(logMessage.toString());

            if (postingRequest.getTransactionid() != null && !postingRequest.getTransactionid().isEmpty() &&
                    (postingRequest.getMasterreference() == null || postingRequest.getMasterreference().isEmpty()) &&
                    (postingRequest.getEventreference() == null || postingRequest.getEventreference().isEmpty())) {
                postingsPage = postingService.getPostingsByTransactionId(postingRequest.getTransactionid(), pageable);
            } else if (postingRequest.getMasterreference() != null && !postingRequest.getMasterreference().isEmpty() &&
                    (postingRequest.getTransactionid() == null || postingRequest.getTransactionid().isEmpty()) &&
                    (postingRequest.getEventreference() == null || postingRequest.getEventreference().isEmpty())) {
                postingsPage = postingService.getPostingsByMasterreference(postingRequest.getMasterreference(), pageable);
            } else if (postingRequest.getTransactionid() != null && !postingRequest.getTransactionid().isEmpty() &&
                    postingRequest.getMasterreference() != null && !postingRequest.getMasterreference().isEmpty() &&
                    (postingRequest.getEventreference() == null || postingRequest.getEventreference().isEmpty())) {
                postingsPage = postingService.getPostingsByTransactionIdAndMasterReference(postingRequest.getTransactionid(), postingRequest.getMasterreference(), pageable);
            } else if (postingRequest.getTransactionid() != null && !postingRequest.getTransactionid().isEmpty() &&
                    postingRequest.getEventreference() != null && !postingRequest.getEventreference().isEmpty() &&
                    (postingRequest.getMasterreference() == null || postingRequest.getMasterreference().isEmpty())) {
                postingsPage = postingService.getPostingsByTransactionidAndEventreference(postingRequest.getTransactionid(), postingRequest.getEventreference(), pageable);
            } else if (postingRequest.getMasterreference() != null && !postingRequest.getMasterreference().isEmpty() &&
                    postingRequest.getEventreference() != null && !postingRequest.getEventreference().isEmpty() &&
                    (postingRequest.getTransactionid() == null || postingRequest.getTransactionid().isEmpty())) {
                postingsPage = postingService.getPostingsByMasterreferenceAndEventreference(postingRequest.getMasterreference(), postingRequest.getEventreference(), pageable);
            } else if (postingRequest.getTransactionid() != null && !postingRequest.getTransactionid().isEmpty() &&
                    postingRequest.getMasterreference() != null && !postingRequest.getMasterreference().isEmpty() &&
                    postingRequest.getEventreference() != null && !postingRequest.getEventreference().isEmpty()) {
                postingsPage = postingService.getPostingsByTransactionidAndMasterreferenceAndEventreference(postingRequest.getTransactionid(), postingRequest.getMasterreference(), postingRequest.getEventreference(), pageable);
            }

            List<PostingDTO> postingsList = postingsPage.getContent();
            response.put("POSTINGSEARCHED", postingsList);
            response.put("totalPages", postingsPage.getTotalPages());
            response.put("totalElements", postingsPage.getTotalElements());

        } catch (Exception e) {
            e.printStackTrace();
            response.put("message", "Erreur lors de la récupération des postings.");
        }

        return response;
    }


}
