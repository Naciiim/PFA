package com.example.homepageBackend.model.mapper;

import com.example.homepageBackend.model.dto.PostingDTO;
import com.example.homepageBackend.model.entity.Posting;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PostingMapper {
   PostingMapper INSTANCE = Mappers.getMapper(PostingMapper.class);

   @Mapping(source = "transactionid", target = "id.transactionid")
   @Mapping(source = "transactionseqno", target = "id.transactionseqno")
   Posting toEntity(PostingDTO postingDTO);


   @Mapping(source = "id.transactionid", target = "transactionid")
   @Mapping(source = "id.transactionseqno", target = "transactionseqno")
   PostingDTO toDto(Posting posting);

   List<PostingDTO> toDtoList(List<Posting> postings);

   List<Posting> toEntityList(List<PostingDTO> postingDTOS);
}
