package br.com.winter.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ComercioRequestDTO {

    @NotBlank(message = "O campo não pode ficar em branco")
    private String nome;

    @Size(min = 2, max = 2, message = "O campo deve constar as iniciais do estado")
    @NotBlank(message = "O campo não pode ficar em branco")
    private String estado;

    @Size(min = 10, max = 11, message = "O campo deve conter um número de celular ou telefone fixo")
    @NotBlank(message = "O campo não pode ficar em branco")
    @Pattern(regexp = "^[0-9]{10,11}$", message = "Apenas números, com DDD")
    private String tel;
}
