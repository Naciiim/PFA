package com.example.homepageBackend.service;

import com.example.homepageBackend.model.dto.PostingDTO;
import com.example.homepageBackend.service.implementation.PostingServiceImpl;
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
public class HomePageService {

    private static final Logger logger = LoggerFactory.getLogger(HomePageService.class);

    @Autowired
    private PostingServiceImpl postingServiceImpl;

    @Autowired
    private FileHandler fileHandler;

    public Map<String, Object> validateAndRetrieveData(PostingDTO posting) {
        Map<String, Object> response = new LinkedHashMap<>();

        // Récupérer tous les postings ayant un état différent de 'T'
        List<PostingDTO> postingsWithDifferentEtat = postingServiceImpl.getPostingsWithDifferentEtat();

        // Utiliser FileHandler pour valider transactionId et masterReference
        if (fileHandler.validateTransactionIdAndMasterReference(posting.getTransactionid(), posting.getMasterreference())) {
            response.put("postingsWithDifferentEtat", postingsWithDifferentEtat);
            return response;
        }

        logger.info("TransactionId: {}", posting.getTransactionid());
        logger.info("MasterReference: {}", posting.getMasterreference());

        // Initialiser les listes de postings
        List<PostingDTO> postingsWithTEtat = null;

        // Récupérer les postings par transactionId si fourni
        if (posting.getTransactionid() != null && !posting.getTransactionid().isEmpty()) {
            logger.info("Récupération des postings par transactionId: {}", posting.getTransactionid());
            List<PostingDTO> postingsDTO = postingServiceImpl.getPostingsByTransactionId(posting.getTransactionid());

            // Utiliser FileHandler pour séparer les postings ayant un état 'T' et les autres
            postingsWithTEtat = postingsDTO.stream()
                    .filter(p -> fileHandler.isEtatT(p.getEtat()))
                    .collect(Collectors.toList());

            // Utiliser FileHandler pour filtrer les postings ayant un état différent de 'T' pour le même transactionId
            List<PostingDTO> postingsWithDifferentEtatForSameTransactionId = postingsDTO.stream()
                    .filter(p -> !fileHandler.isEtatT(p.getEtat()))
                    .collect(Collectors.toList());

            // Ajouter les postings avec un état différent de 'T' pour le même transactionId à postingsWithDifferentEtat
//            postingsWithDifferentEtat.addAll(postingsWithDifferentEtatForSameTransactionId);
        }

        // Si aucun posting n'a été trouvé pour le transactionId donné, retourner les postings avec état différent de 'T'
        if (postingsWithTEtat == null) {
            response.put("postingsWithDifferentEtat", postingsWithDifferentEtat);
            return response;
        }

        // Ajouter les postings avec état 'T' du transactionId dans la réponse
        response.put("postingsWithTEtat", postingsWithTEtat);

        // Ajouter les postings avec état différent de 'T' dans la réponse
        response.put("postingsWithDifferentEtat", postingsWithDifferentEtat);

        // Ajouter un message si des postings ont un état différent de 'T'
        if (!postingsWithDifferentEtat.isEmpty()) {
            StringBuilder message = new StringBuilder();
            message.append("POSTING ");
            message.append("transactionId ").append(posting.getTransactionid());
            message.append(" a des postings différents de 'T' !!!");
            response.put("message", message.toString());
            logger.warn(message.toString());
        }

        return response;
    }
}
