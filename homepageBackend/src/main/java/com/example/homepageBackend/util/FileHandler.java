package com.example.homepageBackend.util;


import com.example.homepageBackend.model.dto.PostingDTO;
import com.example.homepageBackend.model.entity.Posting;
import com.example.homepageBackend.repository.PostingRepository;
import com.example.homepageBackend.service.ExportServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class FileHandler {
    @Autowired
    private final ExportServiceImpl exportServiceImpl;

    public FileHandler(ExportServiceImpl exportServiceImpl) {
        this.exportServiceImpl = exportServiceImpl;
    }


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


}
