package com.example.homepageBackend.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
@NoArgsConstructor
@AllArgsConstructor
@Data
public class PostingDTO {
    private Long id;
    private String transactionId;
    private Date transactionDate;
    private String debitAccount;
    private String creditAccount;
    private String status;
}
