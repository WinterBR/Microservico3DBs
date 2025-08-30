package br.com.winter.DTO;

import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClienteRequestDTO {

    @NotBlank(message = "O campo nome não pode ficar em branco")
    private String nome;

    @Past(message = "A data de nascimento deve ser no passado")
    @NotNull(message = "O campo data de nascimento não pode ficar em branco")
    private LocalDate data;

    @Size(max = 11, min = 11, message = "O campo de CPF deve ter 11 números")
    @NotBlank(message = "O campo CPF não pode ficar em branco")
    private String cpf;

    @Email(message = "O e-mail deve ser válido")
    @NotBlank(message = "O campo email não pode ficar em branco")
    private String email;
}
