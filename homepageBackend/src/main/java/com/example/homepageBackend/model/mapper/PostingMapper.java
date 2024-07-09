package com.example.homepageBackend.model.mapper;

import com.example.homepageBackend.model.dto.PostingDTO;
import com.example.homepageBackend.model.entity.Posting;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PostingMapper {
   PostingMapper INSTANCE = Mappers.getMapper(PostingMapper.class);

   Posting toEntity(PostingDTO postingDTO);

   PostingDTO toDto(Posting posting);
}
