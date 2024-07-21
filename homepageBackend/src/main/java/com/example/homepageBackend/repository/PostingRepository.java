package com.example.homepageBackend.repository;

import com.example.homepageBackend.model.entity.Posting;
import com.example.homepageBackend.model.entity.PostingPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostingRepository extends JpaRepository<Posting, PostingPK> {
  List<Posting> findById_Transactionid(String transactionid);
  List<Posting> findByMasterreference(String masterreference);
  List<Posting> findByEtatNot(String etat);
  List<Posting> findById_TransactionidAndMasterreference(String transactionid, String masterreference);
  List<Posting> findById_TransactionidAndEventreference(String transactionid, String eventreference);
  List<Posting> findByMasterreferenceAndEventreference(String masterreference, String eventreference);
  List<Posting> findById_TransactionidAndMasterreferenceAndEventreference(String transactionid, String masterreference, String eventreference);
}
