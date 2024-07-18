package com.example.homepageBackend.service.interfaces;

import com.example.homepageBackend.model.dto.PostingDTO;
import com.example.homepageBackend.model.entity.Posting;

import java.util.List;

public interface PostingService {
  List<PostingDTO> getPostingsByTransactionId(String transactionId);
 List<PostingDTO> getPostingsByMasterreference(String masterReference);
 List<PostingDTO> getPostingsByTransactionIdAndMasterReference(String transactionid, String masterreference);

 List<PostingDTO> getPostingsWithDifferentEtat() ;


}
