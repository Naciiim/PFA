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
public class Cre {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String paymentId;
    @Column(unique = true)
    private String transactionId;
    private String method;
    @Temporal(TemporalType.DATE)
    private Date date;
    @Column(unique = true)
    private String event;
    private String status;

}
