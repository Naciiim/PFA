package com.example.homepageBackend.util;


import com.example.homepageBackend.repository.MouvementRepository;
import com.example.homepageBackend.repository.MouvementTrfRepository;
import com.example.homepageBackend.repository.PostingRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;


@Component
public class FileHandler {

    private final PostingRepository postingRepository;

private  final MouvementRepository mouvementRepository;
private  final MouvementTrfRepository mouvementTrfRepository;
    @Autowired
    public FileHandler(PostingRepository postingRepository, MouvementRepository mouvementRepository, MouvementTrfRepository mouvementTrfRepository) {
        this.postingRepository = postingRepository;
        this.mouvementRepository = mouvementRepository;
        this.mouvementTrfRepository = mouvementTrfRepository;
    }

    // Valider transactionid et masterreference pour Posting
    public boolean validateTransactionIdAndMasterReference(String transactionid, String masterreference) {
        return (transactionid == null || transactionid.isEmpty()) && (masterreference == null || masterreference.isEmpty());

    }

    // Valider transactionid et reference pour Mouvement
    public boolean validateTransactionIdAndReference(String transactionid, String reference) {
        return (transactionid == null || transactionid.isEmpty()) && (reference == null || reference.isEmpty());

    }

    // Méthode pour vérifier la combinaison cohérente pour Posting
    public boolean isValidPostingCombination(String transactionid, String masterreference) {
        return postingRepository.findById_TransactionidAndMasterreference(transactionid, masterreference, Pageable.unpaged()).hasContent();

    }
    public boolean isValidPostingCombination2(String transactionid, String eventreference) {
        return postingRepository.findById_TransactionidAndEventreference(transactionid, eventreference, Pageable.unpaged()).hasContent();

    }
    public boolean isValidPostingCombination3(String masterreference, String eventreference) {
        return postingRepository.findByMasterreferenceAndEventreference(masterreference, eventreference, Pageable.unpaged()).hasContent();

    }
    public boolean isValidPostingCombination4(String transactionid,String masterreference, String eventreference) {
        return postingRepository.findById_TransactionidAndMasterreferenceAndEventreference(transactionid, masterreference,eventreference, Pageable.unpaged()).hasContent();

    }

    public boolean isValidMouvementCombination(String transactionid, String reference) {
        boolean mouvement = mouvementRepository.findById_TransactionidAndReference(transactionid, reference, Pageable.unpaged()).hasContent();
        boolean mouvementTrf = mouvementTrfRepository.findById_TransactionidAndReference(transactionid, reference, Pageable.unpaged()).hasContent();

        return mouvement || mouvementTrf;
    }
    public boolean isValidMouvementCombination2(String transactionid, String eventreference) {
        boolean mouvement = mouvementRepository.findById_TransactionidAndEventreference(transactionid, eventreference, Pageable.unpaged()).hasContent();
        boolean mouvementTrf = mouvementTrfRepository.findById_TransactionidAndEventreference(transactionid, eventreference, Pageable.unpaged()).hasContent();

        return mouvement || mouvementTrf;
    }
    public boolean isValidMouvementCombination3(String masterreference, String eventreference)
    {
        boolean mouvement = mouvementRepository.findByReferenceAndEventreference(masterreference, eventreference, Pageable.unpaged()).hasContent();
        boolean mouvementTrf = mouvementTrfRepository.findByReferenceAndEventreference(masterreference, eventreference, Pageable.unpaged()).hasContent();

        return mouvement || mouvementTrf;
    }
    public boolean isValidMouvementCombination4(String transactionid,String reference, String eventreference)
    {
        boolean mouvement = mouvementRepository.findById_TransactionidAndReferenceAndEventreference(transactionid,reference, eventreference, Pageable.unpaged()).hasContent();
        boolean mouvementTrf = mouvementTrfRepository.findById_TransactionidAndReferenceAndEventreference(transactionid, reference,eventreference, Pageable.unpaged()).hasContent();

        return mouvement || mouvementTrf;
    }
}
