package br.com.winter.entity;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "tb_produto")
public class Produto {

    @Id
    private ObjectId id;

    @NotBlank(message = "O campo não pode ficar em branco")
    @Field("nome")
    private String nome;

    @NotBlank(message = "O campo não pode ficar em branco")
    @Field("marca")
    private String marca;

    @NotBlank(message = "O campo não pode ficar em branco")
    @Indexed(unique = true)
    @Field("código")
    private String codigo;

    @NotNull(message = "O campo não pode ficar em branco")
    @Digits(integer = 10,fraction = 2, message = "Valor deve ter no máximo 2 casas decimais")
    @Field("valor")
    private Double valor;

}
