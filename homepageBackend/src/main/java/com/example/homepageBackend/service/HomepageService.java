package com.example.homepageBackend.service;

import com.example.homepageBackend.model.dto.CreDTO;
import com.example.homepageBackend.model.dto.MouvementDTO;
import com.example.homepageBackend.model.dto.PostingDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.Map;

@Service
public class HomepageService {

    @Autowired
    private PostingService postingService;

    @Autowired
    private MouvementService mouvementService;

    @Autowired
    private CreService creService;

    public Map<String, Object> validateAndRetrieveData(String transactionId, String ref, String event) {
        Map<String, Object> response = new LinkedHashMap<>();

        // Validate inputs
        if ((transactionId == null || transactionId.isEmpty()) && (ref == null || ref.isEmpty())) {
            response.put("error", "Erreur: Vous devez fournir soit transactionId soit ref.");
            return response;
        }

        // Retrieve Posting
        PostingDTO postingDTO = null;
        if (transactionId != null && !transactionId.isEmpty()) {
            postingDTO = postingService.getPostingByTransactionId(transactionId);
        } else if (ref != null && !ref.isEmpty()) {
            transactionId = mouvementService.getTransactionIdByRef(ref);
            if (transactionId == null) {
                response.put("error", "Erreur: Aucune transaction trouvée pour ref spécifié.");
                return response;
            }
            postingDTO = postingService.getPostingByTransactionId(transactionId);
        }

        // Validate Posting
        if (postingDTO != null) {
            if (!"T".equals(postingDTO.getStatus())) {
                response.put("error", "Erreur: Le statut du posting est différent de 'T'.");
                return response;
            }
            response.put("posting", postingDTO);
        } else {
            response.put("error", "Erreur: Aucune donnée trouvée pour les critères spécifiés.");
            return response;
        }

        // Retrieve Mouvement
        MouvementDTO mouvementDTO = mouvementService.getMouvementByTransactionId(transactionId);
        if (mouvementDTO == null) {
            response.put("error", "Erreur: Aucune donnée trouvée pour les critères spécifiés.");
        } else {
            if (!"T".equals(mouvementDTO.getStatus())) {
                response.put("warning", "Attention: Le statut du mouvement est différent de 'T'.");
            }
            response.put("mouvement", mouvementDTO);
        }

        // Retrieve CRE
        CreDTO creDTO = creService.getCreByTransactionId(transactionId);
        if (creDTO == null) {
            response.put("error", "Erreur: Aucune donnée trouvée pour les critères spécifiés.");
        } else {
            if (!"O".equals(creDTO.getStatus())) {
                response.put("warning", "Attention: Le statut du CRE est différent de 'O'.");
            }
            response.put("cre", creDTO);
        }

        response.put("message", "Vous pouvez maintenant télécharger le fichier EXCEL.");
        return response;
    }
}
