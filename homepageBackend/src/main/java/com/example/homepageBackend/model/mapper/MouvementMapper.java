package com.example.homepageBackend.model.mapper;

import com.example.homepageBackend.model.dto.MouvementDTO;
import com.example.homepageBackend.model.dto.PostingDTO;
import com.example.homepageBackend.model.entity.Mouvement;
import com.example.homepageBackend.model.entity.MouvementTrf;
import com.example.homepageBackend.model.entity.Posting;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MouvementMapper {
    MouvementMapper INSTANCE = Mappers.getMapper(MouvementMapper.class);
    @Mapping(source = "transactionid", target = "id.transactionid")
    @Mapping(source = "transactionseqno", target = "id.transactionseqno")
    Posting toEntity(MouvementDTO mouvementDTO);
    @Mapping(source = "id.transactionid", target = "transactionid")
    @Mapping(source = "id.transactionseqno", target = "transactionseqno")
    MouvementDTO toDto(Mouvement mouvement);
    @Mapping(source = "id.transactionid", target = "transactionid")
    @Mapping(source = "id.transactionseqno", target = "transactionseqno")
    MouvementDTO toDto(MouvementTrf mouvementTrf);
    List<MouvementDTO> toDtoList(List<Mouvement> mouvements);
    List<MouvementDTO> toDtoTrfList(List<MouvementTrf> mouvementTrf);

    List<Mouvement> toEntityList(List<MouvementDTO> mouvementDTOS);
}
