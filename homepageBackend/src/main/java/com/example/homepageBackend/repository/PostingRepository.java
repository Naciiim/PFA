package com.example.homepageBackend.repository;

import com.example.homepageBackend.model.entity.Posting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PostingRepository extends JpaRepository<Posting,Long> {
  Posting findPostingByTransactionId(String transactionId);

}
