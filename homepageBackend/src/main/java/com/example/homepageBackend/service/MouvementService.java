package com.example.homepageBackend.service;

import com.example.homepageBackend.model.dto.MouvementDTO;
import com.example.homepageBackend.model.entity.Mouvement;
import com.example.homepageBackend.model.mapper.MouvementMapper;
import com.example.homepageBackend.repository.MouvementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class MouvementService {

    @Autowired
    private MouvementRepository mouvementRepository;

    private final MouvementMapper mouvementMapper = MouvementMapper.INSTANCE;

    public MouvementDTO getMouvementByTransactionId(String transactionId) {
        Mouvement mouvement = mouvementRepository.findMouvementByTransactionId(transactionId);
        return mouvement != null ? mouvementMapper.toDto(mouvement) : null;
    }

    public String getTransactionIdByRef(String ref) {
        Mouvement mouvement = mouvementRepository.findMouvementByRef(ref);
        if (mouvement != null) {
            return mouvement.getTransactionId();
        }
        return null;
    }


}
