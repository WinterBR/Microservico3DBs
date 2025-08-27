package br.com.winter.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tb_comercio")
public class Comercio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "O campo não pode ficar em branco")
    @Column(name = "nome", unique = true)
    private String nome;

    @Size(min = 2, max = 2, message = "O campo deve constar as iniciais do estado")
    @NotBlank(message = "O campo não pode ficar em branco")
    @Column(name = "estado")
    private String estado;

    @Size(min = 10, max = 11, message = "O campo deve conter um número de celular ou telefone fixo")
    @NotBlank(message = "O campo não pode ficar em branco")
    @Pattern(regexp = "^[0-9]{10,11}$", message = "Apenas números, com DDD")
    @Column(name = "telefone", unique = true)
    private String tel;
}
