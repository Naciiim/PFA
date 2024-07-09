package com.example.homepageBackend.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
@NoArgsConstructor
@AllArgsConstructor
@Data

public class CreDTO {
    private Long id;
    private String paymentId;
    private String transactionId;
    private String method;
    private Date date;
    private String event;
    private String status;
}
