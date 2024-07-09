package com.example.homepageBackend.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class MouvementDTO {
    private Long id;
    private String transactionId;
    private String ref;
    private Double amount;
    private Date date;
    private String event;
    private String status;
}
