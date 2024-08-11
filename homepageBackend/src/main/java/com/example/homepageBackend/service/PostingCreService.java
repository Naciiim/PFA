package com.example.homepageBackend.service;

import com.example.homepageBackend.model.dto.PostingCreDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PostingCreService {
    Page<PostingCreDTO> getPostingCreByTransId(String transId, Pageable pageable);
    Page<PostingCreDTO> getPostingCreByMasterReferences(String masterreference, Pageable pageable);
    Page<PostingCreDTO> getPostingCreByReferences(String masterreference,String eventreference, Pageable pageable);

    Page  <PostingCreDTO> getPostingCresWithDifferentEtat(Pageable pageable);
    List<PostingCreDTO> getAllPostingCreWithDifferentEtat();
}
