package com.example.homepageBackend.repository;

import com.example.homepageBackend.model.entity.MouvementPK;
import com.example.homepageBackend.model.entity.MouvementTrf;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface MouvementTrfRepository extends JpaRepository<MouvementTrf, MouvementPK> {
    Page<MouvementTrf> findById_Transactionid(String transactionid, Pageable pageable);

    Page<MouvementTrf> findByReference(String reference, Pageable pageable);
   List<MouvementTrf> findByEtatNot(String etat);
    Page<MouvementTrf> findById_TransactionidAndReference(String transactionid, String masterreference, Pageable pageable);
    Page<MouvementTrf> findById_TransactionidAndEventreference(String reference, String eventreference, Pageable pageable);
    Page<MouvementTrf> findByReferenceAndEventreference(String reference, String eventreference, Pageable pageable);
    Page<MouvementTrf> findById_TransactionidAndReferenceAndEventreference(String transactionid,String reference,String eventreference, Pageable page);
}
