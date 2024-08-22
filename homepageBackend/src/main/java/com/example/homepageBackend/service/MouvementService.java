package com.example.homepageBackend.service;

import com.example.homepageBackend.model.dto.MouvementDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface MouvementService {
    Page<MouvementDTO> searchMouvement(String reference, Pageable pageable);
    boolean checkIfTrf(String transactionid);
    Page<MouvementDTO> getMouvementsWithDifferentEtat(Pageable pageable);
    Page<MouvementDTO> searchMouvementByTransactionIdAndReference(String transactionid, String reference, Pageable pageable);
    Page<MouvementDTO> searchMouvementByTransactionIdAndReferenceAndEventReference(String transactionid, String reference, String eventreference, Pageable pageable);
    Page<MouvementDTO> searchMouvementByReferenceAndEventReference(String reference, String eventreference, Pageable pageable);
    Page<MouvementDTO> searchMouvementByTransactionIdAndEventReference(String transactionid, String eventreference, Pageable pageable);


    List<MouvementDTO> getAllMouvementsWithDifferentEtat();
}
