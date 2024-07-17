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
    private PostingServiceImpl postingServiceImpl;

    @Autowired
    private FileHandler fileHandler;

    @Override
    public Map<String, Object> validateAndRetrieveData(PostingDTO posting) {
        Map<String, Object> response = new LinkedHashMap<>();

        // Récupérer tous les postings ayant un état différent de 'T'
        List<PostingDTO> postingsWithDifferentEtat = postingServiceImpl.getPostingsWithDifferentEtat();

        // Utiliser FileHandler pour valider transactionId et masterReference
        if (fileHandler.validateTransactionIdAndMasterReference(posting.getTransactionid(), posting.getMasterreference())) {
            response.put("POSTINGSEARCHED", postingsWithDifferentEtat);
            response.put("postingWithDiffEtat", postingsWithDifferentEtat);
            return response;
        }

        logger.info("TransactionId: {}", posting.getTransactionid());
        logger.info("MasterReference: {}", posting.getMasterreference());

        // Initialiser les listes de postings
        List<PostingDTO> postingsSearched = null;

        // Récupérer les postings par transactionId si fourni
        if (posting.getTransactionid() != null && !posting.getTransactionid().isEmpty()) {
            logger.info("Récupération des postings par transactionId: {}", posting.getTransactionid());
            List<PostingDTO> postingsDTO = postingServiceImpl.getPostingsByTransactionId(posting.getTransactionid());

            // Filtrer les postings avec état différent de 'T' pour le même transactionId
            List<PostingDTO> postingsWithTEtat = postingsDTO.stream()
                    .filter(p -> fileHandler.isEtatT(p.getEtat()))
                    .collect(Collectors.toList());

            postingsSearched = postingsDTO.stream()
                    .filter(p -> p.getTransactionid().equals(posting.getTransactionid()))
                    .collect(Collectors.toList());
        }

        // Ajouter les postings avec le même transactionId dans la réponse
        response.put("POSTINGSEARCHED", postingsSearched);

        // Ajouter les postings avec état différent de 'T' dans la réponse
        response.put("postingWithDiffEtat", postingsWithDifferentEtat);

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
