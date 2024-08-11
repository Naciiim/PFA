package com.example.homepageBackend.model.mapper;

import com.example.homepageBackend.model.dto.PostingCreDTO;
import com.example.homepageBackend.model.entity.PostingCre;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PostingCreMapper {
   PostingCreMapper INSTANCE = Mappers.getMapper(PostingCreMapper.class);

   @Mapping(source = "transId", target = "id.transId ")
   @Mapping(source = "typeCre", target = "id.typeCre")
   PostingCre toEntity(PostingCreDTO postingCreDTO);


   @Mapping(source = "id.transId ", target = "transId ")
   @Mapping(source = "id.typeCre", target = "typeCre")
   PostingCreDTO toDto(PostingCre postingCre);

   List<PostingCreDTO> toDtoList(List<PostingCre> postingCres);

   List<PostingCre> toEntityList(List<PostingCreDTO> postingCreDTOS);
}