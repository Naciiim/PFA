package com.example.homepageBackend.service;

import com.example.homepageBackend.model.dto.PostingDTO;
import com.example.homepageBackend.model.dto.PostingRequestDTO;

import java.util.Map;

public interface HomePageService {
     Map<String, Object> validateAndRetrieveData(PostingRequestDTO postingRequest) ;}
