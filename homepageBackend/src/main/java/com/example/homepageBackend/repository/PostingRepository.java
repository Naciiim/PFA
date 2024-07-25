package com.example.homepageBackend.repository;

import com.example.homepageBackend.model.dto.PostingRequestDTO;
import com.example.homepageBackend.model.entity.Posting;
import com.example.homepageBackend.model.entity.PostingPK;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostingRepository extends JpaRepository<Posting, PostingPK> {
  Page<Posting> findById_Transactionid(String transactionid, Pageable pageable);
  Page<Posting> findByMasterreference(String masterreference, Pageable pageable);
  Page<Posting> findById_TransactionidAndMasterreference(String transactionid, String masterreference, Pageable pageable);
  Page<Posting> findById_TransactionidAndEventreference(String transactionid, String eventreference, Pageable pageable);
  Page<Posting> findByMasterreferenceAndEventreference(String masterreference, String eventreference, Pageable pageable);
  Page<Posting> findById_TransactionidAndMasterreferenceAndEventreference(String transactionid, String masterreference, String eventreference, Pageable pageable);
  Page<Posting> findByEtatNot(String etat,Pageable pageable);
}
