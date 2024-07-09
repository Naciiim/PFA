package com.example.homepageBackend.repository;

import com.example.homepageBackend.model.entity.Mouvement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MouvementRepository extends JpaRepository<Mouvement, Long> {

    Mouvement findMouvementByTransactionId(String transactionId);
    Mouvement findMouvementByRef(String ref);
}
