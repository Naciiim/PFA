package com.example.homepageBackend.model.mapper;

import com.example.homepageBackend.model.dto.MouvementDTO;
import com.example.homepageBackend.model.entity.Mouvement;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface MouvementMapper {
    MouvementMapper INSTANCE = Mappers.getMapper(MouvementMapper.class);

    Mouvement toEntity(MouvementDTO mouvementDTO);

    MouvementDTO toDto(Mouvement mouvement);
}
