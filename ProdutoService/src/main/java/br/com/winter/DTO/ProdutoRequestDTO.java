package br.com.winter.DTO;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProdutoRequestDTO {

    @NotBlank(message = "O campo não pode ficar em branco")
    private String nome;

    @NotBlank(message = "O campo não pode ficar em branco")
    private String marca;

    @NotBlank(message = "O campo não pode ficar em branco")
    private String codigo;

    @NotNull(message = "O campo não pode ficar em branco")
    @Digits(integer = 10, fraction = 2, message = "Valor deve ter no máximo 2 casas decimais")
    private Double valor;
}
