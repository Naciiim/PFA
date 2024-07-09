package com.example.homepageBackend.model.mapper;

import com.example.homepageBackend.model.dto.CreDTO;
import com.example.homepageBackend.model.entity.Cre;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CreMapper {
    CreMapper INSTANCE = Mappers.getMapper(CreMapper.class);

    Cre toEntity(CreDTO creDTO);

    CreDTO toDto(Cre cre);
}