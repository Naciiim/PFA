package com.example.homepageBackend.repository;

import com.example.homepageBackend.model.entity.Posting;
import com.example.homepageBackend.model.entity.PostingPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostingRepository extends JpaRepository<Posting, PostingPK> {
  List<Posting> findById_Transactionid(String transactionid);
  List<Posting> findByMasterreference(String masterReference);
  List<Posting> findByEtatNot(String etat);
}
