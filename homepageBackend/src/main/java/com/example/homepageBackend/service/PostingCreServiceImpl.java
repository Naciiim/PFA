package com.example.homepageBackend.service;

import com.example.homepageBackend.model.dto.PostingCreDTO;
import com.example.homepageBackend.model.entity.PostingCre;
import com.example.homepageBackend.model.mapper.PostingCreMapper;
import com.example.homepageBackend.repository.PostingCreRepository;
import com.example.homepageBackend.repository.PostingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class PostingCreServiceImpl implements PostingCreService {
    @Autowired
    private PostingRepository postingRepository;
    @Autowired
    private PostingCreRepository postingCreRepository;
    @Autowired
    private PostingCreMapper postingCreMapper;
    @Override
    public Page<PostingCreDTO>getPostingCreByTransId(String transId, Pageable pageable)
    {
        return postingCreRepository.findById_TransId(transId, pageable)
                .map(postingCreMapper::toDto);
    }
    @Override
    public Page<PostingCreDTO> getPostingCreByMasterReferences(String masterreference, Pageable pageable) {
        // Obtenir les IDs des transactions correspondants sans pagination
        List<String> transactionIds = postingRepository.findAllByMasterreference(masterreference, pageable);

        if (transactionIds.isEmpty()) {
            return Page.empty(pageable);
        }

        // Récupérer les PostingCre pour chaque transaction ID avec pagination
        Set<PostingCre> postingCres = transactionIds.stream()
                .filter(Objects::nonNull)
                .flatMap(trId -> postingCreRepository.findById_TransId(trId, Pageable.unpaged()).getContent().stream())
                .collect(Collectors.toSet());

        // Convertir les PostingCre en PostingCreDTO
        List<PostingCreDTO> postingCreDTOs = postingCres.stream()
                .map(postingCreMapper::toDto)
                .collect(Collectors.toList());

        // Créer une PageImpl avec les données converties
        int totalElements = postingCreDTOs.size();
        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), totalElements);
        List<PostingCreDTO> pageContent = postingCreDTOs.subList(start, end);

        return new PageImpl<>(pageContent, pageable, totalElements);
    }
    @Override
    public Page<PostingCreDTO> getPostingCreByReferences(String masterreference, String eventreference, Pageable pageable) {
        // Obtenir les IDs des transactions correspondants sans pagination
        List<String> transactionIds = postingRepository.findAllByMasterreferenceAndEventreference(masterreference, eventreference,pageable);

        if (transactionIds.isEmpty()) {
            return Page.empty(pageable);
        }

        // Récupérer les PostingCre pour chaque transaction ID avec pagination
        Set<PostingCre> postingCres = transactionIds.stream()
                .filter(Objects::nonNull)
                .flatMap(trId -> postingCreRepository.findById_TransId(trId, Pageable.unpaged()).getContent().stream())
                .collect(Collectors.toSet());

        // Convertir les PostingCre en PostingCreDTO
        List<PostingCreDTO> postingCreDTOs = postingCres.stream()
                .map(postingCreMapper::toDto)
                .collect(Collectors.toList());

        // Créer une PageImpl avec les données converties
        int totalElements = postingCreDTOs.size();
        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), totalElements);
        List<PostingCreDTO> pageContent = postingCreDTOs.subList(start, end);

        return new PageImpl<>(pageContent, pageable, totalElements);
    }



    @Override
  public Page <PostingCreDTO> getPostingCresWithDifferentEtat(Pageable pageable)
    {
        return postingCreRepository.findByEtatNot("O",pageable)
                .map(postingCreMapper::toDto);
    }

  @Override
    public List<PostingCreDTO> getAllPostingCreWithDifferentEtat() {
        List<PostingCreDTO> allPostings = new ArrayList<>();
        int pageNumber = 0;
        int pageSize = 10; // Adjust page size as needed
        Page<PostingCreDTO> page;

        do {
            Pageable pageable = PageRequest.of(pageNumber, pageSize);
            page = postingCreRepository.findByEtatNot("T", pageable).map(postingCreMapper::toDto);
            allPostings.addAll(page.getContent());
            pageNumber++;
        } while (page.hasNext());

        return allPostings;
    }
}
