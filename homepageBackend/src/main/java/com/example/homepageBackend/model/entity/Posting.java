package com.example.homepageBackend.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class Posting {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String transactionId;
    @Temporal(TemporalType.DATE)
    private Date transactionDate;
    private String debitAccount;
    private String creditAccount;
    private String status;

}
