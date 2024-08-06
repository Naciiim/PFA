package com.example.homepageBackend.model.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class MouvementRequestDTO implements Serializable {
    private String transactionid;
    private String reference;
    private String eventreference;
    private int page=0;
    private int size=10;
}