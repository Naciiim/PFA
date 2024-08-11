package com.example.homepageBackend.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class PostingCreDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    private String transId;
    private String typeCre;
    private String chaineCre;
    private String etat;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Date dateCreation;
}
