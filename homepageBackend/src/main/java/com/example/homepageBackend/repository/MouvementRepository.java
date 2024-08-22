package com.example.homepageBackend.repository;

import com.example.homepageBackend.model.entity.Mouvement;
import com.example.homepageBackend.model.entity.MouvementPK;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MouvementRepository extends JpaRepository<Mouvement, MouvementPK> {
    Page<Mouvement> findById_Transactionid(String transactionid, Pageable pageable);
    Page<Mouvement> findByReference(String reference, Pageable pageable);
    List<Mouvement> findByEtatNot(String etat);
    Page<Mouvement> findById_TransactionidAndReference(String transactionid, String reference, Pageable pageable);
    Page<Mouvement> findById_TransactionidAndEventreference(String reference, String eventreference, Pageable pageable);
    Page<Mouvement> findByReferenceAndEventreference(String reference, String eventreference, Pageable pageable);
    Page<Mouvement> findById_TransactionidAndReferenceAndEventreference(String transactionid,String reference,String eventreference, Pageable page);
}
