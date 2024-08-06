package com.example.homepageBackend.service;

import com.example.homepageBackend.model.dto.MouvementDTO;
import com.example.homepageBackend.model.dto.PostingDTO;
import com.example.homepageBackend.model.dto.PostingRequestDTO;
import com.example.homepageBackend.model.entity.Posting;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;


public interface PostingService {
    Page<PostingDTO> getPostingsByTransactionId(String transactionId, Pageable pageable);
    Page<PostingDTO> getPostingsByMasterreference(String masterReference, Pageable pageable);
    Page<PostingDTO> getPostingsByTransactionIdAndMasterReference(String transactionid, String masterreference, Pageable pageable);
    Page<PostingDTO> getPostingsByTransactionidAndEventreference(String transactionid, String eventreference, Pageable pageable);
    Page<PostingDTO> getPostingsByMasterreferenceAndEventreference(String masterreference, String eventreference, Pageable pageable);
    Page<PostingDTO> getPostingsByTransactionidAndMasterreferenceAndEventreference(String transactionid, String masterreference, String eventreference, Pageable pageable);
   Page<PostingDTO> getPostingsWithDifferentEtat(Pageable pageable) ;

  List<PostingDTO> getAllPostingsWithDifferentEtat() ;

    List<MouvementDTO> findMouvementsFromPosting(String transactionid, String masterreference);

    }
