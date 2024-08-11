package com.example.homepageBackend.service;

import com.example.homepageBackend.model.dto.MouvementRequestDTO;
import com.example.homepageBackend.model.dto.PostingCreRequestDTO;
import com.example.homepageBackend.model.dto.PostingRequestDTO;
import org.springframework.data.domain.Pageable;

import java.util.Map;

public interface HomePageService {
     Map<String, Object> findPostings(PostingRequestDTO postingRequest) ;
     Map<String, Object> findMouvements(MouvementRequestDTO mouvementRequestDTO);
     Map<String, Object> findPostingCres(PostingCreRequestDTO postingCreRequestDTO);
}
