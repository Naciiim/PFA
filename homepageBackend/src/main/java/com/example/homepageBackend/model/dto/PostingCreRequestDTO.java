package com.example.homepageBackend.model.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class PostingCreRequestDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    private String transId;
    private String masterreference;
    private String eventreference;
    private int page=0;
    private int size=10;


}
