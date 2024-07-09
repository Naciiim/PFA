package com.example.homepageBackend.service;


import com.example.homepageBackend.model.dto.PostingDTO;
import com.example.homepageBackend.model.entity.Posting;
import com.example.homepageBackend.model.mapper.PostingMapper;
import com.example.homepageBackend.repository.PostingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
public class PostingService {
    @Autowired
    private  PostingRepository postingRepository;

    private final PostingMapper postingMapper = PostingMapper.INSTANCE;

    public PostingDTO getPostingByTransactionId(String transactionId) {
        Optional<Posting> optionalPosting = Optional.ofNullable(postingRepository.findPostingByTransactionId(transactionId));
        return optionalPosting.map(postingMapper::toDto).orElse(null);
    }

}
