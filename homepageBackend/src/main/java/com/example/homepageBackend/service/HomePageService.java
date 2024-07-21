package com.example.homepageBackend.service;

import com.example.homepageBackend.model.dto.PostingDTO;

import java.util.Map;

public interface HomePageService {
    public Map<String, Object> validateAndRetrieveData(PostingDTO posting);
}
