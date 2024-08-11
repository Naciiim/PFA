package com.example.homepageBackend.repository;

import com.example.homepageBackend.model.entity.PostingCre;
import com.example.homepageBackend.model.entity.PostingCrePK;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostingCreRepository extends JpaRepository<PostingCre, PostingCrePK> {
@Query("select pc from POSTINGCRE pc where pc.id.transId like :transId%")
    Page<PostingCre> findById_TransId(String transId, Pageable pageable);
   Page<PostingCre> findByEtatNot(String etat,Pageable pageable);



}
