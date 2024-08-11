package com.example.homepageBackend.repository;

import com.example.homepageBackend.model.dto.PostingRequestDTO;
import com.example.homepageBackend.model.entity.Posting;
import com.example.homepageBackend.model.entity.PostingPK;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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
  @Query("SELECT p.masterreference FROM Posting p WHERE p.id.transactionid = :transactionid")

  List<String> findMasterreferenceById_Transactionid(String transactionid,Pageable pageable);
  @Query("select pc.id.transId from POSTINGCRE pc where substr(pc.id.transId,0,36) in (select distinct p.id.transactionid from Posting p where p.masterreference = :masterreference)")
  List<String> findAllByMasterreference(@Param("masterreference") String masterreference, Pageable pageable);
  @Query("select pc.id.transId from POSTINGCRE pc where substr(pc.id.transId,0,36) in (select distinct p.id.transactionid from Posting p where p.masterreference = :masterreference and p.eventreference=:eventreference)")
  List<String> findAllByMasterreferenceAndEventreference(@Param("masterreference") String masterreference,@Param("eventreference") String eventreference, Pageable pageable);
}
