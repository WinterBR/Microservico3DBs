package br.com.winter.mapper;

import br.com.winter.DTO.ComercioRequestDTO;
import br.com.winter.DTO.ComercioResponseDTO;
import br.com.winter.entity.Comercio;

public class ComercioMapper {

    public static Comercio toEntity(ComercioRequestDTO dto) {
        if (dto == null) return null;
        return Comercio.builder()
                .nome(dto.getNome())
                .estado(dto.getEstado())
                .tel(dto.getTel())
                .build();
    }

    public static ComercioResponseDTO toDTO(Comercio entity) {
        if (entity == null) return null;
        return ComercioResponseDTO.builder()
                .id(entity.getId())
                .nome(entity.getNome())
                .estado(entity.getEstado())
                .tel(entity.getTel())
                .build();
    }
}
