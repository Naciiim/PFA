package com.example.homepageBackend.service;

import com.example.homepageBackend.model.dto.CreDTO;
import com.example.homepageBackend.model.entity.Cre;
import com.example.homepageBackend.model.mapper.CreMapper;
import com.example.homepageBackend.repository.CreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CreService {

    @Autowired
    private CreRepository creRepository;

    private final CreMapper creMapper = CreMapper.INSTANCE;



    public CreDTO getCreByTransactionId(String transactionId) {
        Optional<Cre> optionalCre = Optional.ofNullable(creRepository.findCreByTransactionId(transactionId));
        return optionalCre.map(creMapper::toDto).orElse(null);
    }

}
