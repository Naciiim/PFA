package com.example.homepageBackend.service;

import com.example.homepageBackend.model.dto.PostingDTO;
import com.example.homepageBackend.model.entity.Posting;
import com.example.homepageBackend.model.mapper.PostingMapper;
import com.example.homepageBackend.repository.PostingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostingServiceImpl implements PostingService {
    @Autowired
    private PostingRepository postingRepository;
    @Autowired
    private PostingMapper postingMapper;


    @Override
    public List<PostingDTO> getPostingsByTransactionId(String transactionid) {
        return postingRepository.findById_Transactionid(transactionid).stream()
                .map(postingMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<PostingDTO> getPostingsByMasterreference(String masterreference) {
        return postingRepository.findByMasterreference(masterreference).stream()
                .map(postingMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<PostingDTO> getPostingsByTransactionIdAndMasterReference(String transactionid, String masterreference) {
        return postingRepository.findById_TransactionidAndMasterreference(transactionid, masterreference).stream()
                .map(postingMapper::toDto)
                .collect(Collectors.toList());
    }
    @Override
    public List<PostingDTO> getPostingsByTransactionidAndEventreference(String transactionid, String eventreference) {
        return postingRepository.findById_TransactionidAndEventreference(transactionid, eventreference).stream()
                .map(postingMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<PostingDTO> getPostingsByMasterreferenceAndEventreference(String masterreference, String eventreference) {
        return postingRepository.findByMasterreferenceAndEventreference(masterreference, eventreference)
                .stream()
                .map(postingMapper::toDto)
                .collect(Collectors.toList());
    }
    @Override
    public List<PostingDTO> getPostingsByTransactionidAndMasterreferenceAndEventreference(String transactionid, String masterreference, String eventreference)
    {
        return postingRepository.findById_TransactionidAndMasterreferenceAndEventreference(transactionid, masterreference, eventreference).stream()
                .map(postingMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<PostingDTO> getPostingsWithDifferentEtat() {
        List<Posting> postings = postingRepository.findByEtatNot("T");

        return postings.stream()
                .map(postingMapper::toDto)
                .collect(Collectors.toList());
    }
}
