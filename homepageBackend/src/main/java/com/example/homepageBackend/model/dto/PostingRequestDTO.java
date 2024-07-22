package com.example.homepageBackend.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class PostingRequestDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    private String transactionid;
    private String masterreference;
    private String eventreference;
    private int page=0;
    private int size=10;
}
