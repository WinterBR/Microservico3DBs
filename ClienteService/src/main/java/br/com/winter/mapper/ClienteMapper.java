package br.com.winter.mapper;

import br.com.winter.DTO.ClienteRequestDTO;
import br.com.winter.DTO.ClienteResponseDTO;
import br.com.winter.entity.Cliente;

public class ClienteMapper {

    public static Cliente toEntity(ClienteRequestDTO dto) {
        if (dto == null) return null;
        return Cliente.builder()
                .nome(dto.getNome())
                .data(dto.getData())
                .cpf(dto.getCpf())
                .email(dto.getEmail())
                .build();
    }

    public static ClienteResponseDTO toDTO(Cliente entity) {
        if (entity == null) return null;
        return ClienteResponseDTO.builder()
                .id(entity.getId())
                .nome(entity.getNome())
                .data(entity.getData())
                .cpf(entity.getCpf())
                .email(entity.getEmail())
                .build();
    }
}
