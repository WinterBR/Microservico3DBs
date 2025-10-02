package br.com.winter.mapper;

import br.com.winter.DTO.ProdutoRequestDTO;
import br.com.winter.DTO.ProdutoResponseDTO;
import br.com.winter.entity.Produto;

public class ProdutoMapper {

    public static Produto toEntity(ProdutoRequestDTO dto) {
        if (dto == null) return null;
        return Produto.builder()
                .nome(dto.getNome())
                .marca(dto.getMarca())
                .codigo(dto.getCodigo())
                .valor(dto.getValor())
                .build();
    }

    public static ProdutoResponseDTO toDTO(Produto entity) {
        if (entity == null) return null;
        return ProdutoResponseDTO.builder()
                .id(entity.getId() != null ? entity.getId().toHexString() : null)
                .nome(entity.getNome())
                .marca(entity.getMarca())
                .codigo(entity.getCodigo())
                .valor(entity.getValor())
                .build();
    }
}
