package com.example.homepageBackend.service;

import com.example.homepageBackend.model.dto.PostingDTO;
import com.example.homepageBackend.model.dto.PostingRequestDTO;
import com.example.homepageBackend.model.entity.Posting;
import com.example.homepageBackend.model.mapper.PostingMapper;
import com.example.homepageBackend.repository.PostingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
@Service
public class PostingServiceImpl implements PostingService {

    @Autowired
    private PostingRepository postingRepository;

    @Autowired
    private PostingMapper postingMapper;

    @Override
    public Page<PostingDTO> getPostingsByTransactionId(String transactionid, Pageable pageable) {
        return postingRepository.findById_Transactionid(transactionid, pageable).map(postingMapper::toDto);
    }

    @Override
    public Page<PostingDTO> getPostingsByMasterreference(String masterreference, Pageable pageable) {
        return postingRepository.findByMasterreference(masterreference, pageable).map(postingMapper::toDto);
    }

    @Override
    public Page<PostingDTO> getPostingsByTransactionIdAndMasterReference(String transactionid, String masterreference, Pageable pageable) {
        return postingRepository.findById_TransactionidAndMasterreference(transactionid, masterreference, pageable).map(postingMapper::toDto);
    }

    @Override
    public Page<PostingDTO> getPostingsByTransactionidAndEventreference(String transactionid, String eventreference, Pageable pageable) {
        return postingRepository.findById_TransactionidAndEventreference(transactionid, eventreference, pageable).map(postingMapper::toDto);
    }

    @Override
    public Page<PostingDTO> getPostingsByMasterreferenceAndEventreference(String masterreference, String eventreference, Pageable pageable) {
        return postingRepository.findByMasterreferenceAndEventreference(masterreference, eventreference, pageable).map(postingMapper::toDto);
    }

    @Override
    public Page<PostingDTO> getPostingsByTransactionidAndMasterreferenceAndEventreference(String transactionid, String masterreference, String eventreference, Pageable pageable) {
        return postingRepository.findById_TransactionidAndMasterreferenceAndEventreference(transactionid, masterreference, eventreference, pageable).map(postingMapper::toDto);
    }

    @Override
    public Page<PostingDTO> getPostingsWithDifferentEtat(Pageable pageable) {
        return postingRepository.findByEtatNot("T", pageable).map(postingMapper::toDto);
    }

    @Override
    public List<PostingDTO> getAllPostingsWithDifferentEtat() {
        List<PostingDTO> allPostings = new ArrayList<>();
        int pageNumber = 0;
        int pageSize = 100; // Adjust page size as needed
        Page<PostingDTO> page;

        do {
            Pageable pageable = PageRequest.of(pageNumber, pageSize);
            page = postingRepository.findByEtatNot("T", pageable).map(postingMapper::toDto);
            allPostings.addAll(page.getContent());
            pageNumber++;
        } while (page.hasNext());

        return allPostings;
    }

}

