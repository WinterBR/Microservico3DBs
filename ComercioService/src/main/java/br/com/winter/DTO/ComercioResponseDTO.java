package br.com.winter.DTO;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ComercioResponseDTO {
    private Long id;
    private String nome;
    private String estado;
    private String tel;
}
