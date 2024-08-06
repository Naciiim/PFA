package com.example.homepageBackend.service;

import com.example.homepageBackend.model.dto.MouvementDTO;
import com.example.homepageBackend.model.entity.Mouvement;
import com.example.homepageBackend.model.entity.MouvementTrf;
import com.example.homepageBackend.model.mapper.MouvementMapper;
import com.example.homepageBackend.repository.MouvementRepository;
import com.example.homepageBackend.repository.MouvementTrfRepository;
import com.example.homepageBackend.repository.PostingRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;


@Service
public class MouvementServiceImpl implements MouvementService {

    @Autowired
    private MouvementRepository mouvementRepository;

    @Autowired
    private MouvementTrfRepository mouvementTrfRepository;

    @Autowired
    private PostingRepository postingRepository;

    @Autowired
    private MouvementMapper mouvementMapper;
    private static final Logger logger = LoggerFactory.getLogger(MouvementServiceImpl.class);


    @Override
    public Page<MouvementDTO> searchMouvement(String reference, Pageable pageable) {
        if (reference.startsWith("TRF")) {
            return mouvementTrfRepository.findByReference(reference, pageable)
                    .map(mouvementMapper::toDto);
        } else {
            return mouvementRepository.findByReference(reference, pageable)
                    .map(mouvementMapper::toDto);
        }
    }

   @Override
    public boolean checkIfTrf(String transactionid) {
        List<String> masterReferences = postingRepository.findMasterreferenceById_Transactionid(transactionid,Pageable.unpaged());
        for (String reference : masterReferences) {
            if (reference.startsWith("TRF")) {
                return true;
            }
        }
        return false;
    }


    @Override
    public Page<MouvementDTO> searchMouvementByTransactionIdAndReference(String transactionid, String reference, Pageable pageable) {
        boolean isTrf = checkIfTrf(transactionid);
        if (isTrf) {
            return mouvementTrfRepository.findById_TransactionidAndReference(transactionid, reference, pageable)
                    .map(mouvementMapper::toDto);
        } else {
            return mouvementRepository.findById_TransactionidAndReference(transactionid, reference, pageable)
                    .map(mouvementMapper::toDto);
        }
    }

    @Override
    public Page<MouvementDTO> searchMouvementByTransactionIdAndReferenceAndEventReference(String transactionid, String reference, String eventreference, Pageable pageable) {
        boolean isTrf = checkIfTrf(transactionid);
        if (isTrf) {
            return mouvementTrfRepository.findById_TransactionidAndReferenceAndEventreference(transactionid, reference, eventreference, pageable)
                    .map(mouvementMapper::toDto);
        } else {
            return mouvementRepository.findById_TransactionidAndReferenceAndEventreference(transactionid, reference, eventreference, pageable)
                    .map(mouvementMapper::toDto);
        }
    }

    @Override
    public Page<MouvementDTO> searchMouvementByReferenceAndEventReference(String reference, String eventreference, Pageable pageable) {
        if (reference.startsWith("TRF")) {
              return mouvementTrfRepository.findByReferenceAndEventreference(reference, eventreference, pageable)
                    .map(mouvementMapper::toDto);
        } else {
            return mouvementRepository.findByReferenceAndEventreference(reference, eventreference, pageable)
                    .map(mouvementMapper::toDto);
        }
    }
    @Override
    public Page<MouvementDTO> searchMouvementByTransactionIdAndEventReference(String transactionid, String eventreference, Pageable pageable) {
        boolean isTrf = checkIfTrf(transactionid);
        if (isTrf) {
            return mouvementTrfRepository.findById_TransactionidAndEventreference(transactionid,eventreference, pageable)
                    .map(mouvementMapper::toDto);
        } else {
            return mouvementRepository.findById_TransactionidAndEventreference(transactionid,  eventreference, pageable)
                    .map(mouvementMapper::toDto);
        }
    }

    @Override
    public Page<MouvementDTO> getMouvementsWithDifferentEtat(Pageable pageable) {
        // Récupérer les mouvements de l'entité Mouvement
        List<Mouvement> mouvements = mouvementRepository.findByEtatNot("T");
        List<MouvementDTO> mouvementDTOs = mouvementMapper.toDtoList(mouvements);

        // Récupérer les mouvements de l'entité MouvementTrf
        List<MouvementTrf> mouvementTrfs = mouvementTrfRepository.findByEtatNot("T");
        List<MouvementDTO> mouvementTrfDTOs = mouvementMapper.toDtoTrfList(mouvementTrfs);

        // Combiner les deux listes de mouvements
        List<MouvementDTO> allMouvements = new ArrayList<>(mouvementDTOs);
        allMouvements.addAll(mouvementTrfDTOs);

        // Trier la liste combinée par ordre chronologique (ou autre critère)
        allMouvements.sort(Comparator.comparing(MouvementDTO::getTransactionid).reversed());

        // Créer une nouvelle page avec les mouvements combinés
        int totalElements = allMouvements.size();
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int totalPages = (int) Math.ceil((double) totalElements / pageSize);

        int start = Math.min(currentPage * pageSize, totalElements);
        int end = Math.min(start + pageSize, totalElements);
        List<MouvementDTO> pageContent = allMouvements.subList(start, end);

        return new PageImpl<>(pageContent, PageRequest.of(currentPage, pageSize), totalElements);
    }

    @Override
    public List<MouvementDTO> getAllMouvementsWithDifferentEtat() {
        List<MouvementDTO> allMouvements = new ArrayList<>();
        int pageNumber = 0;
        int pageSize = 10; // Adjust page size as needed
        Page<MouvementDTO> page;

        do {
            Pageable pageable = PageRequest.of(pageNumber, pageSize);
            page = getMouvementsWithDifferentEtat(pageable);
            allMouvements.addAll(page.getContent());
            pageNumber++;
        } while (page.hasNext());

        return allMouvements;
    }
}



