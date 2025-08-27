package br.com.winter.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tb_cliente")
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O campo não pode ficar em branco")
    @Column(name = "nome")
    private String nome;

    @Past
    @NotNull(message = "O campo não pode ficar em branco")
    @Column(name = "data_de_nascimento")
    private LocalDate data;

    @Size(max = 11, min = 11, message = "O campo de CPF deve ter 11 números")
    @NotBlank(message = "O campo não pode ficar em branco")
    @Column(name = "cpf", unique = true)
    private String cpf;

    @Email
    @NotBlank(message = "O campo não pode ficar em branco")
    @Column(name = "email", unique = true)
    private String email;

}
