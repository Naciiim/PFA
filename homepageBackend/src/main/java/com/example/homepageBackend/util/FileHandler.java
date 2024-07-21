package com.example.homepageBackend.util;


import com.example.homepageBackend.model.entity.Posting;
import com.example.homepageBackend.repository.PostingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class FileHandler {
    @Autowired
    private PostingRepository postingRepository;


    // Vérification si etat == 'T'
    public boolean isEtatT(String etat) {
        return "T".equals(etat);
    }

    // Filtrer les postings avec un etat différent de 'T'
    public List<Posting> filterInvalidPostings(List<Posting> postings) {
        return postings.stream()
                .filter(posting -> !"T".equals(posting.getEtat()))
                .collect(Collectors.toList());
    }


    // Valider transactionId et masterReference
    public boolean validateTransactionIdAndMasterReference(String transactionId, String masterReference) {
        return (transactionId == null || transactionId.isEmpty()) && (masterReference == null || masterReference.isEmpty());
    }
    // Valider transactionId et eventReference
    public boolean validateTransactionIdAndEventReference(String transactionId, String eventReference) {
        return (transactionId == null || transactionId.isEmpty()) && (eventReference == null || eventReference.isEmpty());
    }

    // Valider masterReference et eventReference
    public boolean validateMasterReferenceAndEventReference(String masterReference, String eventReference) {
        return (masterReference == null || masterReference.isEmpty()) && (eventReference == null || eventReference.isEmpty());
    }

}
