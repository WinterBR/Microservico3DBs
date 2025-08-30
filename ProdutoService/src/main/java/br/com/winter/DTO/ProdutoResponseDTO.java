package br.com.winter.DTO;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProdutoResponseDTO {
    private String id;
    private String nome;
    private String marca;
    private String codigo;
    private Double valor;
}
