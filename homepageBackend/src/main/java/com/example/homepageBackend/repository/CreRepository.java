package com.example.homepageBackend.repository;

import com.example.homepageBackend.model.entity.Cre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CreRepository extends JpaRepository<Cre,Long> {
     Cre findCreByTransactionId(String transactionId);

}
