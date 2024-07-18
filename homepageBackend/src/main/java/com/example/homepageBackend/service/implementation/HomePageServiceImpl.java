package com.example.homepageBackend.service.implementation;

import com.example.homepageBackend.model.dto.PostingDTO;
import com.example.homepageBackend.service.interfaces.HomePageService;
import com.example.homepageBackend.util.FileHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service
public class HomePageServiceImpl implements HomePageService {

    private static final Logger logger = LoggerFactory.getLogger(HomePageServiceImpl.class);

    @Autowired
    private PostingServiceImpl postingService;

    @Autowired
    private FileHandler fileHandler;

    @Override
    public Map<String, Object> validateAndRetrieveData(PostingDTO posting) {
        Map<String, Object> response = new LinkedHashMap<>();

        // Récupérer tous les postings ayant un état différent de 'T'
        List<PostingDTO> postingsWithDifferentEtat = postingService.getPostingsWithDifferentEtat();

        // Vérifier et récupérer les postings selon transactionid et/ou masterreference
        if (posting.getTransactionid() != null && !posting.getTransactionid().isEmpty() &&
                (posting.getMasterreference() == null || posting.getMasterreference().isEmpty())) {
            // Recherche par transactionid uniquement
            logger.info("Récupération des postings par transactionId: {}", posting.getTransactionid());
            List<PostingDTO> postingsByTransactionId = postingService.getPostingsByTransactionId(posting.getTransactionid());

            response.put("POSTINGSEARCHED", postingsByTransactionId);
        } else if (posting.getMasterreference() != null && !posting.getMasterreference().isEmpty() &&
                (posting.getTransactionid() == null || posting.getTransactionid().isEmpty())) {
            // Recherche par masterreference uniquement
            logger.info("Récupération des postings par masterreference: {}", posting.getMasterreference());
            List<PostingDTO> postingsByMasterReference = postingService.getPostingsByMasterreference(posting.getMasterreference());

            response.put("POSTINGSEARCHED", postingsByMasterReference);
        } else if (posting.getTransactionid() != null && !posting.getTransactionid().isEmpty() &&
                posting.getMasterreference() != null && !posting.getMasterreference().isEmpty()) {
            // search  par transactionid et masterreference
            logger.info("Récupération des postings par transactionId: {} et masterreference: {}",
                    posting.getTransactionid(), posting.getMasterreference());
            List<PostingDTO> postingsByTransactionIdAndMasterReference = postingService.getPostingsByTransactionIdAndMasterReference(
                    posting.getTransactionid(), posting.getMasterreference());

            response.put("POSTINGSEARCHED", postingsByTransactionIdAndMasterReference);
        } else {
            // Aucun critère de recherche fourni
            logger.warn("Aucun critère de recherche valide fourni.");
            response.put("message", "Veuillez fournir au moins un critère de recherche valide (transactionid ou masterreference).");
        }

        // Ajouter les postings avec état différent de 'T' dans la réponse
        response.put("postingWithDiffEtat", postingsWithDifferentEtat);

        // Ajouter un message si des postings ont un état différent de 'T'
        if (!postingsWithDifferentEtat.isEmpty()) {
            StringBuilder message = new StringBuilder();
            message.append("Des postings ont un état différent de 'T' pour les critères fournis.");
            response.put("message", message.toString());
            logger.warn(message.toString());
        }

        return response;
    }
}

