package com.example.homepageBackend.service;

import com.example.homepageBackend.model.dto.*;

import com.example.homepageBackend.model.mapper.MouvementMapper;
import com.example.homepageBackend.repository.MouvementRepository;
import com.example.homepageBackend.repository.MouvementTrfRepository;
import com.example.homepageBackend.util.FileHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Profile(value = {"DEV", "PROD", "REC"})
public class HomePageServiceImpl implements HomePageService {

    private static final Logger logger = LoggerFactory.getLogger(HomePageServiceImpl.class);

    @Autowired
    private PostingServiceImpl postingService;
    @Autowired
    private MouvementServiceImpl mouvementServiceImpl;
    @Autowired
    private MouvementRepository mouvementRepository;
    @Autowired
    private MouvementTrfRepository mouvementTrfRepository;
    @Autowired
    private MouvementMapper mouvementMapper;

    @Autowired
    private PostingCreServiceImpl postingCreServiceImpl;

    @Autowired
    private FileHandler fileHandler;

    @Override
    public Map<String, Object> findPostings(PostingRequestDTO postingRequest) {
        Map<String, Object> response = new LinkedHashMap<>();
        Pageable pageable = PageRequest.of(postingRequest.getPage(), postingRequest.getSize());

        try {
            Page<PostingDTO> postingsWithDifferentEtat = postingService.getPostingsWithDifferentEtat(pageable);
            List<PostingDTO> postingsListWithDifferentEtat = postingsWithDifferentEtat.getContent();
            response.put("postingWithDiffEtat", postingsListWithDifferentEtat);
            response.put("totalPagesWithDiffEtat", postingsWithDifferentEtat.getTotalPages());
            response.put("totalElementsWithDiffEtat", postingsWithDifferentEtat.getTotalElements());

            if (fileHandler.validateTransactionIdAndMasterReference(postingRequest.getTransactionid(), postingRequest.getMasterreference())) {

                logger.warn("Validation échouée pour transactionid: {} et masterreference: {}",
                        postingRequest.getTransactionid(), postingRequest.getMasterreference());
                response.put("message", "Transaction ID et Master Reference ne peuvent pas être tous les deux vides.");
                return response;
            }

            Page<PostingDTO> postingsPage = null;
            StringBuilder logMessage = new StringBuilder("Récupération des postings par ");

            if (postingRequest.getTransactionid() != null && !postingRequest.getTransactionid().isEmpty()) {
                logMessage.append("transactionid: ").append(postingRequest.getTransactionid()).append(" ");
            }
            if (postingRequest.getMasterreference() != null && !postingRequest.getMasterreference().isEmpty()) {
                logMessage.append("masterreference: ").append(postingRequest.getMasterreference()).append(" ");
            }
            if (postingRequest.getEventreference() != null && !postingRequest.getEventreference().isEmpty()) {
                logMessage.append("eventreference: ").append(postingRequest.getEventreference()).append(" ");
            }

            logger.info(logMessage.toString());

            if (postingRequest.getTransactionid() != null && !postingRequest.getTransactionid().isEmpty() &&
                    (postingRequest.getMasterreference() == null || postingRequest.getMasterreference().isEmpty()) &&
                    (postingRequest.getEventreference() == null || postingRequest.getEventreference().isEmpty())) {
                postingsPage = postingService.getPostingsByTransactionId(postingRequest.getTransactionid(), pageable);
            } else if (postingRequest.getMasterreference() != null && !postingRequest.getMasterreference().isEmpty() &&
                    (postingRequest.getTransactionid() == null || postingRequest.getTransactionid().isEmpty()) &&
                    (postingRequest.getEventreference() == null || postingRequest.getEventreference().isEmpty())) {
                postingsPage = postingService.getPostingsByMasterreference(postingRequest.getMasterreference(), pageable);
            } else if (postingRequest.getTransactionid() != null && !postingRequest.getTransactionid().isEmpty() &&
                    postingRequest.getMasterreference() != null && !postingRequest.getMasterreference().isEmpty() &&
                    (postingRequest.getEventreference() == null || postingRequest.getEventreference().isEmpty())) {
                boolean isValid = fileHandler.isValidPostingCombination(postingRequest.getTransactionid(), postingRequest.getMasterreference());
                if (!isValid) {
                    response.put("message", "La combinaison de Transaction ID et Master Reference n'est pas valide.");
                    return response;
                }
                postingsPage = postingService.getPostingsByTransactionIdAndMasterReference(postingRequest.getTransactionid(), postingRequest.getMasterreference(), pageable);
            } else if (postingRequest.getTransactionid() != null && !postingRequest.getTransactionid().isEmpty() &&
                    postingRequest.getEventreference() != null && !postingRequest.getEventreference().isEmpty() &&
                    (postingRequest.getMasterreference() == null || postingRequest.getMasterreference().isEmpty())) {
                boolean isValid = fileHandler.isValidPostingCombination2(postingRequest.getTransactionid(), postingRequest.getEventreference());
                if (!isValid) {
                    response.put("message", "La combinaison de Transaction ID et Event Reference n'est pas valide.");
                    return response;
                }
                postingsPage = postingService.getPostingsByTransactionidAndEventreference(postingRequest.getTransactionid(), postingRequest.getEventreference(), pageable);
            } else if (postingRequest.getMasterreference() != null && !postingRequest.getMasterreference().isEmpty() &&
                    postingRequest.getEventreference() != null && !postingRequest.getEventreference().isEmpty() &&
                    (postingRequest.getTransactionid() == null || postingRequest.getTransactionid().isEmpty())) {
                boolean isValid = fileHandler.isValidPostingCombination3(postingRequest.getMasterreference(), postingRequest.getEventreference());
                if (!isValid) {
                    response.put("message", "La combinaison de Masterreference et Event Reference n'est pas valide.");
                    return response;
                }
                postingsPage = postingService.getPostingsByMasterreferenceAndEventreference(postingRequest.getMasterreference(), postingRequest.getEventreference(), pageable);
            } else if (postingRequest.getTransactionid() != null && !postingRequest.getTransactionid().isEmpty() &&
                    postingRequest.getMasterreference() != null && !postingRequest.getMasterreference().isEmpty() &&
                    postingRequest.getEventreference() != null && !postingRequest.getEventreference().isEmpty()) {
                boolean isValid = fileHandler.isValidPostingCombination4(postingRequest.getTransactionid(), postingRequest.getMasterreference(), postingRequest.getEventreference());
                if (!isValid) {
                    response.put("message", "La combinaison n'est pas valide.");
                    return response;
                }
                postingsPage = postingService.getPostingsByTransactionidAndMasterreferenceAndEventreference(postingRequest.getTransactionid(), postingRequest.getMasterreference(), postingRequest.getEventreference(), pageable);
            }

            List<PostingDTO> postingsList = postingsPage.getContent();
            response.put("POSTINGSEARCHED", postingsList);
            response.put("totalPages", postingsPage.getTotalPages());
            response.put("totalElements", postingsPage.getTotalElements());

        } catch (Exception e) {
            e.printStackTrace();
            response.put("message", "Erreur lors de la récupération des postings.");
        }

        return response;
    }

    @Override
    public Map<String, Object> findMouvements(MouvementRequestDTO mouvementRequestDTO) {
        Map<String, Object> response = new LinkedHashMap<>();
        Pageable pageable = PageRequest.of(mouvementRequestDTO.getPage(), mouvementRequestDTO.getSize());

        try {
            // Récupération des mouvements avec état différent de 'T'
            Page<MouvementDTO> mouvementsWithDifferentEtat = mouvementServiceImpl.getMouvementsWithDifferentEtat(pageable);
            List<MouvementDTO> mouvementsListWithDifferentEtat = mouvementsWithDifferentEtat.getContent();
            response.put("mvtWithEtatDiff", mouvementsListWithDifferentEtat);
            response.put("totalPagesWithEtatDiff", mouvementsWithDifferentEtat.getTotalPages());
            response.put("totalElementsWithEtatDiff", mouvementsWithDifferentEtat.getTotalElements());

            // Validation des valeurs
            if (fileHandler.validateTransactionIdAndReference(mouvementRequestDTO.getTransactionid(), mouvementRequestDTO.getReference())) {
                logger.warn("Validation échouée pour transactionid: {} et reference: {}", mouvementRequestDTO.getTransactionid(), mouvementRequestDTO.getReference());
                response.put("message", "Transaction ID et Reference ne peuvent pas être tous les deux vides.");
                return response;
            }

            Page<MouvementDTO> mouvementsPage = null;

            // Construction du message de log
            StringBuilder logMessage = new StringBuilder("Récupération des mouvements par ");
            if (mouvementRequestDTO.getTransactionid() != null && !mouvementRequestDTO.getTransactionid().isEmpty()) {
                logMessage.append("transactionid: ").append(mouvementRequestDTO.getTransactionid()).append(" ");
            }
            if (mouvementRequestDTO.getReference() != null && !mouvementRequestDTO.getReference().isEmpty()) {
                logMessage.append("reference: ").append(mouvementRequestDTO.getReference()).append(" ");
            }
            if (mouvementRequestDTO.getEventreference() != null && !mouvementRequestDTO.getEventreference().isEmpty()) {
                logMessage.append("eventreference: ").append(mouvementRequestDTO.getEventreference()).append(" ");
            }
            logger.info(logMessage.toString());

            // Logique de recherche basée sur les valeurs de transactionid, reference, et eventreference
            if (mouvementRequestDTO.getTransactionid() != null && !mouvementRequestDTO.getTransactionid().isEmpty() &&
                    (mouvementRequestDTO.getReference() == null || mouvementRequestDTO.getReference().isEmpty()) &&
                    (mouvementRequestDTO.getEventreference() == null || mouvementRequestDTO.getEventreference().isEmpty())) {

                if (mouvementServiceImpl.checkIfTrf(mouvementRequestDTO.getTransactionid())) {
                    mouvementsPage = mouvementTrfRepository.findById_Transactionid(mouvementRequestDTO.getTransactionid(), pageable)
                            .map(mouvementMapper::toDto);
                } else {
                    mouvementsPage = mouvementRepository.findById_Transactionid(mouvementRequestDTO.getTransactionid(), pageable)
                            .map(mouvementMapper::toDto);
                }

            } else if (mouvementRequestDTO.getReference() != null && !mouvementRequestDTO.getReference().isEmpty() &&
                    (mouvementRequestDTO.getTransactionid() == null || mouvementRequestDTO.getTransactionid().isEmpty()) &&
                    (mouvementRequestDTO.getEventreference() == null || mouvementRequestDTO.getEventreference().isEmpty())) {

                mouvementsPage = mouvementServiceImpl.searchMouvement(mouvementRequestDTO.getReference(), pageable);

            } else if (mouvementRequestDTO.getTransactionid() != null && !mouvementRequestDTO.getTransactionid().isEmpty() &&
                    mouvementRequestDTO.getReference() != null && !mouvementRequestDTO.getReference().isEmpty() &&
                    (mouvementRequestDTO.getEventreference() == null || mouvementRequestDTO.getEventreference().isEmpty())) {

                boolean isValid = fileHandler.isValidMouvementCombination(mouvementRequestDTO.getTransactionid(), mouvementRequestDTO.getReference());
                if (!isValid) {
                    response.put("message", "La combinaison de Transaction ID et Reference n'est pas valide.");
                    return response;
                }
                mouvementsPage = mouvementServiceImpl.searchMouvementByTransactionIdAndReference(mouvementRequestDTO.getTransactionid(), mouvementRequestDTO.getReference(), pageable);

            } else if (mouvementRequestDTO.getTransactionid() != null && !mouvementRequestDTO.getTransactionid().isEmpty() &&
                    mouvementRequestDTO.getEventreference() != null && !mouvementRequestDTO.getEventreference().isEmpty() &&
                    (mouvementRequestDTO.getReference() == null || mouvementRequestDTO.getReference().isEmpty())) {

                boolean isValid = fileHandler.isValidMouvementCombination2(mouvementRequestDTO.getTransactionid(), mouvementRequestDTO.getEventreference());
                if (!isValid) {
                    response.put("message", "La combinaison de Transaction ID et Event Reference n'est pas valide.");
                    return response;
                }
                mouvementsPage = mouvementServiceImpl.searchMouvementByTransactionIdAndEventReference(mouvementRequestDTO.getTransactionid(), mouvementRequestDTO.getEventreference(), pageable);

            } else if (mouvementRequestDTO.getReference() != null && !mouvementRequestDTO.getReference().isEmpty() &&
                    mouvementRequestDTO.getEventreference() != null && !mouvementRequestDTO.getEventreference().isEmpty() &&
                    (mouvementRequestDTO.getTransactionid() == null || mouvementRequestDTO.getTransactionid().isEmpty())) {

                boolean isValid = fileHandler.isValidMouvementCombination3(mouvementRequestDTO.getReference(), mouvementRequestDTO.getEventreference());
                if (!isValid) {
                    response.put("message", "La combinaison de Reference et Event Reference n'est pas valide.");
                    return response;
                }
                mouvementsPage = mouvementServiceImpl.searchMouvementByReferenceAndEventReference(mouvementRequestDTO.getReference(), mouvementRequestDTO.getEventreference(), pageable);

            } else if (mouvementRequestDTO.getTransactionid() != null && !mouvementRequestDTO.getTransactionid().isEmpty() &&
                    mouvementRequestDTO.getReference() != null && !mouvementRequestDTO.getReference().isEmpty() &&
                    mouvementRequestDTO.getEventreference() != null && !mouvementRequestDTO.getEventreference().isEmpty()) {

                boolean isValid = fileHandler.isValidMouvementCombination4(mouvementRequestDTO.getTransactionid(), mouvementRequestDTO.getReference(), mouvementRequestDTO.getEventreference());
                if (!isValid) {
                    response.put("message", "La combinaison n'est pas valide.");
                    return response;
                }
                mouvementsPage = mouvementServiceImpl.searchMouvementByTransactionIdAndReferenceAndEventReference(mouvementRequestDTO.getTransactionid(), mouvementRequestDTO.getReference(), mouvementRequestDTO.getEventreference(), pageable);
            }

            if (mouvementsPage != null) {
                response.put("mvtSearched", mouvementsPage.getContent());
                response.put("totalPages", mouvementsPage.getTotalPages());
                response.put("totalElements", mouvementsPage.getTotalElements());
            }

        } catch (Exception e) {
            logger.error("Erreur lors de la récupération des mouvements.", e);
            response.put("message", "Erreur lors de la récupération des mouvements.");
        }

        return response;
    }

    @Override
    public Map<String, Object> findPostingCres(PostingCreRequestDTO postingCreRequestDTO) {
        Map<String, Object> response = new LinkedHashMap<>();
        Pageable pageable = PageRequest.of(postingCreRequestDTO.getPage(), postingCreRequestDTO.getSize());

        try {
            Page<PostingCreDTO> postingCreWithDifferentEtat = postingCreServiceImpl.getPostingCresWithDifferentEtat(pageable);
            List<PostingCreDTO> postingCresListWithDifferentEtat = postingCreWithDifferentEtat.getContent();
            response.put("postingCreWithDiffEtat", postingCresListWithDifferentEtat);
            response.put("totalPagesWithDiffEtat", postingCreWithDifferentEtat.getTotalPages());
            response.put("totalElementsWithDiffEtat", postingCreWithDifferentEtat.getTotalElements());

            if (fileHandler.validateTransactionIdAndMasterReference(postingCreRequestDTO.getTransId(), postingCreRequestDTO.getMasterreference())) {

                logger.warn("Validation échouée pour transactionid: {} et masterreference: {}",
                        postingCreRequestDTO.getTransId(), postingCreRequestDTO.getMasterreference());
                response.put("message", "Transaction ID et Master Reference ne peuvent pas être tous les deux vides.");
                return response;
            }

            Page<PostingCreDTO> postingCresPage = null;
            StringBuilder logMessage = new StringBuilder("Récupération des postingCres par ");

            if (postingCreRequestDTO.getTransId() != null && !postingCreRequestDTO.getTransId().isEmpty()) {
                logMessage.append("transactionid: ").append(postingCreRequestDTO.getTransId()).append(" ");
            }
            if (postingCreRequestDTO.getMasterreference() != null && !postingCreRequestDTO.getMasterreference().isEmpty()) {
                logMessage.append("masterreference: ").append(postingCreRequestDTO.getMasterreference()).append(" ");
            }
            if (postingCreRequestDTO.getEventreference() != null && !postingCreRequestDTO.getEventreference().isEmpty()) {
                logMessage.append("eventreference: ").append(postingCreRequestDTO.getEventreference()).append(" ");
            }

            logger.info(logMessage.toString());

            if (postingCreRequestDTO.getTransId() != null && !postingCreRequestDTO.getTransId().isEmpty() &&
                    (postingCreRequestDTO.getMasterreference() == null || postingCreRequestDTO.getMasterreference().isEmpty()) &&
                    (postingCreRequestDTO.getEventreference() == null || postingCreRequestDTO.getEventreference().isEmpty())) {
                //recherche par transactionID
                postingCresPage = postingCreServiceImpl.getPostingCreByTransId(postingCreRequestDTO.getTransId(), pageable);
            } else if (postingCreRequestDTO.getMasterreference() != null && !postingCreRequestDTO.getMasterreference().isEmpty() &&
                    (postingCreRequestDTO.getTransId() == null || postingCreRequestDTO.getTransId().isEmpty()) &&
                    (postingCreRequestDTO.getEventreference() == null || postingCreRequestDTO.getEventreference().isEmpty())) {
                //recherche par masterReference
                postingCresPage = postingCreServiceImpl.getPostingCreByMasterReferences(postingCreRequestDTO.getMasterreference(), pageable);
            } else if (postingCreRequestDTO.getTransId() != null && !postingCreRequestDTO.getTransId().isEmpty() &&
                    postingCreRequestDTO.getMasterreference() != null && !postingCreRequestDTO.getMasterreference().isEmpty() &&
                    (postingCreRequestDTO.getEventreference() == null || postingCreRequestDTO.getEventreference().isEmpty())) {
                //recherche par transactionID+masterReference
                postingCresPage = postingCreServiceImpl.getPostingCreByMasterReferences(postingCreRequestDTO.getMasterreference(), pageable);
            } else if (postingCreRequestDTO.getTransId() != null && !postingCreRequestDTO.getTransId().isEmpty() &&
                    postingCreRequestDTO.getEventreference() != null && !postingCreRequestDTO.getEventreference().isEmpty() &&
                    (postingCreRequestDTO.getMasterreference() == null || postingCreRequestDTO.getMasterreference().isEmpty())) {
                //recherche par transactionID+eventReference
                postingCresPage = postingCreServiceImpl.getPostingCreByTransId(postingCreRequestDTO.getTransId(), pageable);
            } else if (postingCreRequestDTO.getMasterreference() != null && !postingCreRequestDTO.getMasterreference().isEmpty() &&
                    postingCreRequestDTO.getEventreference() != null && !postingCreRequestDTO.getEventreference().isEmpty() &&
                    (postingCreRequestDTO.getTransId() == null || postingCreRequestDTO.getTransId().isEmpty())) {
                //recherche par masterReference+eventReference
                     postingCresPage = postingCreServiceImpl.getPostingCreByReferences(postingCreRequestDTO.getMasterreference(),postingCreRequestDTO.getEventreference(),  pageable);
            } else if (postingCreRequestDTO.getTransId() != null && !postingCreRequestDTO.getTransId().isEmpty() &&
                    postingCreRequestDTO.getMasterreference() != null && !postingCreRequestDTO.getMasterreference().isEmpty() &&
                    postingCreRequestDTO.getEventreference() != null && !postingCreRequestDTO.getEventreference().isEmpty()) {
                //recherche par transactionID+masterReference+eventReference
                    postingCresPage = postingCreServiceImpl.getPostingCreByReferences(postingCreRequestDTO.getMasterreference(),postingCreRequestDTO.getEventreference(), pageable);
            }

            List<PostingCreDTO> postingsList = postingCresPage.getContent();
            response.put("postingCreSearched", postingsList);
            response.put("totalPages", postingCresPage.getTotalPages());
            response.put("totalElements", postingCresPage.getTotalElements());

        } catch (Exception e) {
            e.printStackTrace();
            response.put("message", "Erreur lors de la récupération des mouvement.");
        }

        return response;
    }

}
